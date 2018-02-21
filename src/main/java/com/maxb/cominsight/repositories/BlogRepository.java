package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.essential.Blog;
import com.maxb.cominsight.models.essential.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog, String> {

    Page<Blog> findAllByCompanyId(ObjectId id, Pageable pageable);

    Page<Blog> findAllByMemberId(ObjectId id, Pageable pageable);
}
