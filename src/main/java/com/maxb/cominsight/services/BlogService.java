package com.maxb.cominsight.services;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.essential.Blog;
import org.springframework.data.domain.Page;

public interface BlogService {

    Page<Blog> getAll(int page, int size) ;

    Page<Blog> getForMember(String userId, int page, int size);

    Page<Blog> getForCompany(String companyId, int page, int size);

    Blog save(String username, Blog blog, String companyId) throws EntityNotFoundException;

    void delete(Blog blog);

    void delete(String id);

    Blog find(String id);
}
