package com.maxb.cominsight.services;


import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.VerificationToken;
import com.maxb.cominsight.models.essential.User;
import org.springframework.data.domain.Page;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> getUsers();

    User saveUser(User user);

    User updateAvatar(String username, MultipartFile multipartFile) throws EntityNotFoundException, IOException;

    void deleteUser(User user);

    void deleteUser(String id);

    User findUser(String id);

    User findByEmail(String email);

    Page<User> findByCompanyId(String companyId, int page, int size);

    VerificationToken getVerificationToken(String VerificationToken);

    void createVerificationToken(User user, String token);

    User resetPassword(String email, WebRequest request) throws EntityNotFoundException;

    User updatePassword(String userId, String token, String password) throws EntityNotFoundException;

    User changePassword(String username, String oldPassword, String rawPassword) throws EntityNotFoundException;
}
