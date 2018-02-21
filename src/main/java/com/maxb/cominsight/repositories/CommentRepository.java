package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.essential.Comment;
import com.maxb.cominsight.models.essential.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    Page<Comment> findByPostId(String id, Pageable pageable);

    Page<Comment> findByBlogId(String id, Pageable pageable);

}
