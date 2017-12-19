package com.maxb.cominsight.services;


import com.maxb.cominsight.models.essential.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User saveUser(User user);

    void deleteUser(User user);

    void deleteUser(String id);

    User findUser(String id);
}
