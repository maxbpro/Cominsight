package com.maxb.cominsight.repositories;


import com.maxb.cominsight.models.essential.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
