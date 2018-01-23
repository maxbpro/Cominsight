package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Gender;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.Photo;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.repositories.CompanyRepository;
import com.maxb.cominsight.repositories.PhotoRepository;
import com.maxb.cominsight.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    static final String SECRET = "secretkey";
    static final String TOKEN_PREFIX = "Bearer";

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostConstruct
    private void init(){

        Company company = new Company();
        company.setEmail("evdakov@gmail.com");
        company.setTitle("Rutorika");
        company.setPhone("123456");
        company.setAddress("Zipovsk 5");
        company.setUrl("http://rutorika.ru");
        companyRepository.save(company);

        User user = new User();
        user.setUsername("maxb2009");
        user.setEmail("maxbpro2009@gmail.com");
        user.setFirstName("Max");
        user.setLastName("Biuanow");
        user.setPassword(encoder.encode("1234"));
        user.setCompany(company);
        user.setGender(Gender.MALE);

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        usersRepository.save(user);

        for(int i = 0; i < 30; i++){
            Photo photo = new Photo();
            photo.setUser(user);
            photo.setCompany(company);
            photo.setCreated(LocalDateTime.now());
            photo.setPhotoUrl("http://www.kino-teatr.ru/news/13642/128725.jpg");
            photoRepository.save(photo);
        }
    }



    /**
     * This method will return the logged user.
     *
     * @param principal
     * @return Principal java security principal object
     */
    @RequestMapping("/user")
    public User user(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return usersRepository.findByUsername(loggedUsername);
    }

    /**
     * @param username
     * @param password
     * @return JSON contains token and user after success authentication.
     * @throws IOException
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username,
                                                     @RequestParam String password) throws IOException {

        User appUser = usersRepository.findByUsername(username);

        if (appUser == null) {
            throw new RuntimeException("User doesn't exist");
        }

        return getResponseEntry(appUser, username, password);

    }



    /**
     * This method is used for user registration. Note: user registration is not
     * require any authentication.
     *
     * @param appUser
     * @return JSON contains token and user after success authentication.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody User appUser) throws EntityNotFoundException {

        if (usersRepository.findByUsername(appUser.getUsername()) != null) {
            throw new RuntimeException("Username already exist");
        }

        if(appUser.getCompany() != null){
            Company company = companyRepository.findOne(appUser.getCompany().getId());

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

        return getResponseEntry(savedUser, appUser.getUsername(), rawPassword);
    }




    private ResponseEntity<Map<String, Object>> getResponseEntry(User appUser, String username, String rawPassword){

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
            tokenMap.put("token", null);
            return new ResponseEntity<>(tokenMap, HttpStatus.UNAUTHORIZED);
        }
    }
}
