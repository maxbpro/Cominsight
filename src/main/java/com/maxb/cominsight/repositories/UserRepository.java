package com.maxb.cominsight.repositories;


import com.maxb.cominsight.models.essential.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    User findByEmail(String email);

    Page<User> findByCompanyId(ObjectId companyId, Pageable pageable);


    Page<User> findByCompanyTitle(String title, Pageable pageable);
}
