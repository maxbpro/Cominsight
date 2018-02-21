package com.maxb.cominsight.services.impl;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.essential.Blog;
import com.maxb.cominsight.models.essential.Comment;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.repositories.BlogRepository;
import com.maxb.cominsight.repositories.CommentRepository;
import com.maxb.cominsight.repositories.CompanyRepository;
import com.maxb.cominsight.repositories.UserRepository;
import com.maxb.cominsight.services.BlogService;
import com.maxb.cominsight.services.CommentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Page<Blog> getAll(int page, int size) {
        return repository.findAll(new PageRequest(page, size));
    }

    @Override
    public Page<Blog> getForMember(String userId, int page, int size) {
        return repository.findAllByMemberId(new ObjectId(userId), new PageRequest(page, size));
    }

    @Override
    public Page<Blog> getForCompany(String companyId, int page, int size) {
        return repository.findAllByCompanyId(new ObjectId(companyId), new PageRequest(page, size));
    }



    @Override
    public Blog save(String username, Blog blog, String companyId) throws EntityNotFoundException {

        User user = userRepository.findByUsername(username);
        blog.setMember(user);

        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new EntityNotFoundException(User.class);
        }

        blog.setCompany(company);

        return repository.save(blog);
    }

    @Override
    public void delete(Blog blog) {
        repository.delete(blog);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public Blog find(String id) {
        return repository.findOne(id);
    }
}
