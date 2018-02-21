package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.VerificationToken;
import com.maxb.cominsight.models.essential.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
