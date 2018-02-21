package com.maxb.cominsight.controllers;

import com.maxb.cominsight.config.exceptions.EntityNotFoundException;
import com.maxb.cominsight.models.Follower;
import com.maxb.cominsight.models.dto.CommentDTO;
import com.maxb.cominsight.models.dto.CommentReplayDTO;
import com.maxb.cominsight.models.dto.CompanyDTO;
import com.maxb.cominsight.models.essential.*;
import com.maxb.cominsight.services.CommentService;
import com.maxb.cominsight.services.CompanyService;
import com.maxb.cominsight.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CommentController {


    @Autowired
    private CommentService commentService;


    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/comments/post/{postId}", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<CommentDTO> getCommentsOfPost(@PathVariable("postId") String postId,
                                         @RequestParam( "page" ) int page, @RequestParam( "size" ) int size) {
        return commentService.findByPostId(postId, page, size)
                .map(this::convertCommentToDto);

    }

    @RequestMapping(value = "/comments/blog/{blogId}", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<CommentDTO> getCommentsOfBlog(@PathVariable("blogId") String blogId,
                                        @RequestParam( "page" ) int page, @RequestParam( "size" ) int size) {
        return commentService.findByBlogId(blogId, page, size)
                .map(this::convertCommentToDto);

    }

    @RequestMapping(value = "/comments/replays/{commentId}", params = { "page", "size" }, method = RequestMethod.GET)
    public Page<CommentReplayDTO> getReplays(@PathVariable("commentId") String commentId,
                                        @RequestParam( "page" ) int page, @RequestParam( "size" ) int size) {
        return commentService.findByCommentId(commentId, page, size)
                .map(this::convertCommentReplayToDto);

    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public CommentDTO newComment(@Valid @RequestBody CommentDTO commentDTO,
                                 Principal principal) throws EntityNotFoundException{

        Comment comment = convertCommentToEntity(commentDTO);
        Comment savedComment = commentService.saveComment(principal.getName(), comment, comment.getPost().getId());
        return convertCommentToDto(savedComment);

    }


    @RequestMapping(value = "/comments/replay", method = RequestMethod.POST)
    public CommentReplayDTO newReplay(@Valid @RequestBody CommentReplayDTO commentReplayDTO,
                                 Principal principal) throws EntityNotFoundException{

        CommentReplay replay = convertCommentReplayToEntity(commentReplayDTO);
        CommentReplay savedReplay = commentService.saveCommentReplay(principal.getName(), replay,
                replay.getComment().getId());
        return convertCommentReplayToDto(savedReplay);
    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteComment(@PathVariable("id") String id) {
        commentService.deleteComment(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/replays/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCommentReplay(@PathVariable("id") String id) {
        commentService.deleteCommentReplay(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private CommentDTO convertCommentToDto(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        return commentDTO;
    }

    private Comment convertCommentToEntity(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        return comment;
    }


    private CommentReplayDTO convertCommentReplayToDto(CommentReplay commentReplay) {
        CommentReplayDTO commentReplayDTO = modelMapper.map(commentReplay, CommentReplayDTO.class);
        return commentReplayDTO;
    }

    private CommentReplay convertCommentReplayToEntity(CommentReplayDTO replayDTO) {
        CommentReplay replay = modelMapper.map(replayDTO, CommentReplay.class);
        return replay;
    }

}
