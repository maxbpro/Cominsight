package com.maxb.cominsight.services;

import com.maxb.cominsight.models.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {

    Page<Comment> getComments(int page, int size);

    Comment saveComment(Comment company);

    void deleteComment(Comment company);

    void deleteComment(String id);

    Comment findComment(String id);
}
