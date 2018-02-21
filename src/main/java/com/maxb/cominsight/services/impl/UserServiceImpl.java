package com.maxb.cominsight.services.impl;


import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.PasswordResetToken;
import com.maxb.cominsight.models.VerificationToken;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.repositories.PasswordResetTokenRepository;
import com.maxb.cominsight.repositories.UserRepository;
import com.maxb.cominsight.repositories.VerificationTokenRepository;
import com.maxb.cominsight.services.StorageService;
import com.maxb.cominsight.services.UserService;
import org.bson.types.ObjectId;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.maxb.cominsight.models.VerificationToken.EXPIRATION;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository resetTokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messages;

    @Autowired
    private Environment env;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User " + username + " not found.");
        }

        return user;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.delete(id);
    }

    @Override
    public User findUser(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<User> findByCompanyId(String companyId, int page, int size) {
        return userRepository.findByCompanyId(new ObjectId(companyId), new PageRequest(page, size));
    }

    @Override
    public User updateAvatar(String username, MultipartFile multipartFile) throws EntityNotFoundException, IOException {

        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new EntityNotFoundException(User.class);
        }

        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        BufferedImage image = ImageIO.read(convFile);
        BufferedImage photoLg = Scalr.resize(image, 800);
        BufferedImage photoMd = Scalr.resize(image, 150);
        BufferedImage photoSm = Scalr.resize(image, 80);
        BufferedImage photoXs = Scalr.resize(image, 50);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String postfix = user.getRegisteredDate().format(formatter);

        String avatarImageLg = username + postfix + "-avatar-lg";
        String avatarImageMd = username + postfix + "-avatar-md";
        String avatarImageSm = username + postfix + "-avatar-sm";
        String avatarImageXs = username + postfix + "-avatar-xs";

        storageService.store(photoLg, avatarImageLg);
        storageService.store(photoMd, avatarImageMd);
        storageService.store(photoSm, avatarImageSm);
        storageService.store(photoXs, avatarImageXs);


        user.setAvatar(avatarImageMd);

        return userRepository.save(user);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }



    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        Date date = verificationToken.calculateExpiryDate(EXPIRATION);
        verificationToken.setExpiryDate(date);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public User resetPassword(String email, WebRequest request) throws EntityNotFoundException{

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException(User.class);
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);

        try{
            mailSender.send(constructResetTokenEmail(request.getLocale(), token, user));

        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }

        return user;
    }

    private void createPasswordResetTokenForUser(User user, String token){

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        Date date = passwordResetToken.calculateExpiryDate(EXPIRATION);
        passwordResetToken.setExpiryDate(date);
        resetTokenRepository.save(passwordResetToken);
    }

    private SimpleMailMessage constructResetTokenEmail(Locale locale, String token, User user) {

        String url = env.getProperty("endpoint") + "/update_password/" + user.getId() + "/" + token;
        String message = messages.getMessage("message.resetPassword", null, locale);

        String messageBody = message + " \r\n" + url;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Reset Password");
        email.setText(messageBody);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;


    }

    @Override
    public User updatePassword(String userId, String token, String password) throws EntityNotFoundException{

        User user = validatePasswordResetToken(userId, token);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    private User validatePasswordResetToken(String userId, String token) throws EntityNotFoundException {

        PasswordResetToken passToken = resetTokenRepository.findByToken(token);
        if ((passToken == null) || (!passToken.getUser().getId().equals(userId))) {
            throw new EntityNotFoundException(PasswordResetToken.class);
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new EntityNotFoundException(PasswordResetToken.class);
        }

        User user = userRepository.findOne(passToken.getUser().getId());
        //TODO why?
//        Authentication auth = new UsernamePasswordAuthenticationToken(
//                user, null, Arrays.asList(
//                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
        return user;
    }

    public User changePassword(String username, String oldPassword, String rawPassword) throws EntityNotFoundException{

        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new EntityNotFoundException(User.class);
        }

        if(encoder.matches(oldPassword, user.getPassword())){

            String encodedPassword = encoder.encode(rawPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);

        }else{
            throw new EntityNotFoundException(User.class);
        }

        return user;
    }

}
