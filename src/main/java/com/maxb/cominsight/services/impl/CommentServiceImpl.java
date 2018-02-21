package com.maxb.cominsight.services.impl;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.essential.Comment;
import com.maxb.cominsight.models.essential.CommentReplay;
import com.maxb.cominsight.models.essential.Post;
import com.maxb.cominsight.models.essential.User;
import com.maxb.cominsight.repositories.CommentReplayRepository;
import com.maxb.cominsight.repositories.CommentRepository;
import com.maxb.cominsight.repositories.PostRepository;
import com.maxb.cominsight.repositories.UserRepository;
import com.maxb.cominsight.services.CommentService;
import com.maxb.cominsight.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentReplayRepository replayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Comment> findByPostId(String id, int page, int size) {
        return commentRepository.findByPostId(id, new PageRequest(page, size));
    }

    @Override
    public Page<Comment> findByBlogId(String id, int page, int size) {
        return commentRepository.findByBlogId(id, new PageRequest(page, size));
    }

    @Override
    public Page<Comment> getComments(int page, int size) {
        return commentRepository.findAll(new PageRequest(page, size));
    }

    @Override
    public Comment saveComment(String username, Comment comment, String postId) throws EntityNotFoundException {

        User user = userRepository.findByUsername(username);
        comment.setMember(user);

        Post post = postRepository.findOne(postId);

        if(post == null){
            throw new EntityNotFoundException(Post.class);
        }

        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteComment(String id) {
        commentRepository.delete(id);
    }

    @Override
    public Comment findComment(String id) {
        return commentRepository.findOne(id);
    }




    //

    @Override
    public Page<CommentReplay> findByCommentId(String id, int page, int size) {
        return replayRepository.findByCommentId(id, new PageRequest(page, size));
    }

    @Override
    public CommentReplay saveCommentReplay(String username, CommentReplay replay, String commentId) throws EntityNotFoundException {

        User user = userRepository.findByUsername(username);
        replay.setMember(user);

        Comment comment = commentRepository.findOne(commentId);

        if(comment == null){
            throw new EntityNotFoundException(Post.class);
        }

        replay.setComment(comment);

        return replayRepository.save(replay);
    }

    @Override
    public void deleteCommentReplay(String id) {
        replayRepository.delete(id);
    }

}
