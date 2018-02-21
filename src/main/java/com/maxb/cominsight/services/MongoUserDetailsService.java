package com.maxb.cominsight.services;

import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = usersRepository.findByUsername(usernameOrEmail);

        if(user == null){

            user = usersRepository.findByEmail(usernameOrEmail);

            if(user == null){
                throw new UsernameNotFoundException("User not found");
            }else{
                return user;
            }

        }else{
            return user;
        }
    }
}
