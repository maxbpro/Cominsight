package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.events.OnRegistrationCompleteEvent;
import com.maxb.cominsight.models.SuccessResult;
import com.maxb.cominsight.models.VerificationToken;
import com.maxb.cominsight.models.dto.MemberDTO;
import com.maxb.cominsight.models.dto.UserDTO;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.repositories.UserRepository;
import com.maxb.cominsight.services.CompanyService;
import com.maxb.cominsight.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    static final String SECRET = "secretkey";
    static final String TOKEN_PREFIX = "Bearer";

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ApplicationEventPublisher eventPublisher;



    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username,
                                                     @RequestParam String password) throws EntityNotFoundException {

        User user = usersRepository.findByUsername(username);

        if (user == null) {
            user = usersRepository.findByEmail(username);
            if (user == null) {
                throw new EntityNotFoundException(User.class);
            }
        }

        return getResponseEntry(user, user.getUsername(), password);
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserDTO userDTO,
                                                        WebRequest request) throws EntityNotFoundException {

        if (usersRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new RuntimeException("Username already exist");
        }

        User appUser = convertToUserEntity(userDTO);

        if(userDTO.getCompany() != null){
            Company company = companyService.findCompany(userDTO.getCompany().getId());

            if(company == null){
                throw new RuntimeException("Company doesn't exist");
            }

            appUser.setCompany(company);

        }else{
            throw new RuntimeException("Company doesn't exist");
        }

        List<String> roles = new ArrayList<>(Arrays.asList("USER"));
        appUser.setRoles(roles);

        String rawPassword = appUser.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        appUser.setPassword(encodedPassword);

        User savedUser = usersRepository.save(appUser);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedUser, request.getLocale(), appUrl));

        return getResponseEntry(savedUser, appUser.getUsername(), rawPassword);
    }




    private ResponseEntity<Map<String, Object>> getResponseEntry(User appUser, String username, String rawPassword) throws EntityNotFoundException{

        Map<String, Object> tokenMap = new HashMap<>();

        if (appUser != null && encoder.matches(rawPassword, appUser.getPassword())) {
            String token = Jwts.builder()
                    .setSubject(username)
                    .claim("roles", appUser.getRoles()).setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();

            tokenMap.put("token", token);
            tokenMap.put("user", appUser);
            return new ResponseEntity<>(tokenMap, HttpStatus.OK);
        }else {
            throw new EntityNotFoundException(User.class);
        }
    }

    @RequestMapping(value = "/registrationConfirm/{token}", method = RequestMethod.GET)
    public UserDTO confirmRegistration (@PathVariable("token") String token) throws EntityNotFoundException {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            throw new EntityNotFoundException(VerificationToken.class);
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new EntityNotFoundException(VerificationToken.class);
        }

        user.setEnabled(true);
        userService.saveUser(user);

        return convertToUserDto(user);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public UserDTO resetPassword(@RequestParam("email") String email, WebRequest request) throws EntityNotFoundException {
        return convertToUserDto(userService.resetPassword(email, request));
    }


    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.GET)
    public UserDTO updatePassword(@RequestParam("userId") String userId,
                                  @RequestParam("token") String token,
                                  @RequestParam("password") String rawPassword) throws EntityNotFoundException {

        String encodedPassword = encoder.encode(rawPassword);
        return convertToUserDto(userService.updatePassword(userId, token, encodedPassword));
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public UserDTO changePassword(@RequestParam("old") String oldPassword,
                                  @RequestParam("password") String rawPassword,
                                  Principal principal) throws EntityNotFoundException {

        return convertToUserDto(userService.changePassword(principal.getName(), oldPassword, rawPassword));
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public MemberDTO getUserById(@PathVariable("id") String id) throws EntityNotFoundException {
        User oldUser = userService.findUser(id);
        if (oldUser == null) {
            throw new EntityNotFoundException(User.class);
        }

        return convertToDto(oldUser);
    }

    @RequestMapping(value = "/users/company/{companyId}", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<MemberDTO> allMembersOfCompany(@PathVariable("companyId") String companyId,
                                               @RequestParam( "page" ) int page,
                                               @RequestParam( "size" ) int size) {
        return userService.findByCompanyId(companyId, page, size)
                .map(this::convertToDto);
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public UserDTO updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) throws EntityNotFoundException {

        User oldUser = userService.findUser(id);
        if (oldUser == null) {
            throw new EntityNotFoundException(User.class);
        }
        oldUser.setFirstName (userDTO.getFirstName());
        oldUser.setLastName(userDTO.getLastName());
        oldUser.setGender(userDTO.getGender());
        oldUser.setEmail(userDTO.getEmail());
        oldUser.setText(userDTO.getText());

        return convertToUserDto(userService.saveUser(oldUser));
    }

    @RequestMapping(value = "/users/update/avatar", method = RequestMethod.PUT)
    public UserDTO updateAvatar(@RequestPart("image") MultipartFile multipartFile ,
                           Principal principal) throws EntityNotFoundException, IOException {

        return convertToUserDto(userService.updateAvatar(principal.getName(), multipartFile));
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public SuccessResult deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return new SuccessResult("User has been deleted!");
    }



    private MemberDTO convertToDto(User user) {
        MemberDTO memberDTO = modelMapper.map(user, MemberDTO.class);
        return memberDTO;
    }

    private User convertToEntity(MemberDTO memberDTO) {
        User user = modelMapper.map(memberDTO, User.class);
        return user;
    }

    private UserDTO convertToUserDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    private User convertToUserEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

}
