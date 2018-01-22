package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.SuccessResult;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> allUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") String id) throws EntityNotFoundException {
        User oldUser = userService.findUser(id);
        if (oldUser == null) {
            throw new EntityNotFoundException(User.class);
        }

        return oldUser;
    }



    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable("id") String id, @RequestBody User user) throws EntityNotFoundException {

        User oldUser = userService.findUser(id);
        if (oldUser == null) {
            throw new EntityNotFoundException(User.class);
        }
        oldUser.setFirstName (user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setUsername(user.getUsername());
        oldUser.setAnonymity(user.isAnonymity());
        oldUser.setGender(user.getGender());
        oldUser.setEmail(user.getEmail());

        return userService.saveUser(oldUser);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public SuccessResult deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return new SuccessResult("User has been deleted!");
    }


//    public User sendInvite(){
//
//    }
//
//    public User acceptInvite(){
//
//    }
//
//    public User removeInvite(){
//
//    }
}
