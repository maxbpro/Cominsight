package com.maxb.cominsight.services.impl;

import com.maxb.cominsight.models.Comment;
import com.maxb.cominsight.models.essential.Company;
import com.maxb.cominsight.models.essential.Photo;
import com.maxb.cominsight.repositories.CommentRepository;
import com.maxb.cominsight.repositories.CompanyRepository;
import com.maxb.cominsight.services.CommentService;
import com.maxb.cominsight.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Override
    public Page<Comment> getComments(int page, int size) {
        return repository.findAll(new PageRequest(page, size));
    }

    @Override
    public Comment saveComment(Comment company) {
        return repository.save(company);
    }

    @Override
    public void deleteComment(Comment company) {
        repository.delete(company);
    }

    @Override
    public void deleteComment(String id) {
        repository.delete(id);
    }

    @Override
    public Comment findComment(String id) {
        return repository.findOne(id);
    }
}
