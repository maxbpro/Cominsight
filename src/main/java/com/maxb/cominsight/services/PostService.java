package com.maxb.cominsight.services;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Following;
import com.maxb.cominsight.models.essential.Post;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {


    Page<Post> getAll(int page, int size) ;

    Page<Post> getForUser(String username, int page, int size);

    Page<Post> getForMember(String userId, int page, int size);

    Page<Post> getForCompany(String companyId, int page, int size);


    Page<Following> getFollowingForMember(String userId, int page, int size);

    Post save(String username, MultipartFile multipartFile, Post post, String companyId) throws EntityNotFoundException, IOException;

    void delete(Post post);

    void delete(String id);

    Post find(String id);
}
