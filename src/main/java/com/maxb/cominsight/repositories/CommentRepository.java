package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.Comment;
import com.maxb.cominsight.models.essential.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
