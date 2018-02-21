package com.maxb.cominsight.services;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.essential.Comment;
import com.maxb.cominsight.models.essential.CommentReplay;
import org.springframework.data.domain.Page;

public interface CommentService {

    Page<Comment> findByPostId(String id, int page, int size);

    Page<Comment> findByBlogId(String id, int page, int size);

    Page<Comment> getComments(int page, int size);

    Comment saveComment(String username, Comment comment, String postId) throws EntityNotFoundException;

    void deleteComment(Comment company);

    void deleteComment(String id);

    Comment findComment(String id);

    //

    Page<CommentReplay> findByCommentId(String id, int page, int size);

    CommentReplay saveCommentReplay(String username, CommentReplay replay, String commentId)
            throws EntityNotFoundException;

    void deleteCommentReplay(String id);
}
