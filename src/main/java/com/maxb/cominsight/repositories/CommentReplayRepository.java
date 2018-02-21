package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.essential.CommentReplay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentReplayRepository extends MongoRepository<CommentReplay, String> {

    Page<CommentReplay> findByCommentId(String id, Pageable pageable);
}
