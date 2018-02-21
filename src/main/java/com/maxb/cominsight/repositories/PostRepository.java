package com.maxb.cominsight.repositories;

import com.maxb.cominsight.models.essential.Post;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findAllByCompanyIdInOrderByCreatedAsc(List<String> ids, Pageable pageable);

    Page<Post> findAllByCompanyTitleInOrderByCreatedAsc(List<String> titles, Pageable pageable);

    Page<Post> findAllByCompanyId(ObjectId id, Pageable pageable);

    Page<Post> findAllByMemberId(ObjectId id, Pageable pageable);

//    Page<Post> findAllByphotoUrl(String photoUrl, Pageable pageable);
//
//    @Query(value = "{ 'company.id' : ?0 }", fields = "{ 'company.id' : 0 }")
//    Page<Post> findInArrayIds(List<String> ids, Pageable pageable);
//
//    @Query(value = "{ 'company.title' : ?0 }", fields = "{ 'company.title' : 0 }")
//    Page<Post> findInArrayTitles(List<String> titles, Pageable pageable);

}
