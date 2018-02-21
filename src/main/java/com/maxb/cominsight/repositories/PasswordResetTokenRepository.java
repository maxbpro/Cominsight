package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.PasswordResetToken;
import com.maxb.cominsight.models.essential.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}
