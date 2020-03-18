package com.sda.microblogging.controller;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.CommentLike;
import com.sda.microblogging.entity.DTO.comment.CommentDTO;
import com.sda.microblogging.entity.DTO.comment.CommentNewInputDTO;
import com.sda.microblogging.entity.DTO.comment.CommentSavedDTO;
import com.sda.microblogging.entity.mapper.CommentDTOMapper;
import com.sda.microblogging.exception.ParentCommentNotFoundException;
import com.sda.microblogging.exception.PostNotFoundException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.service.CommentLikeService;
import com.sda.microblogging.service.CommentService;
import com.sda.microblogging.service.PostService;
import com.sda.microblogging.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/microblogging/v1")
public class CommentController {

    private CommentService commentService;
    private CommentLikeService commentLikeService;
    private CommentDTOMapper commentDtoMapper;
    private PostService postService;
    private UserService userService;

    @Autowired
    public CommentController(
            CommentService commentService,
            CommentLikeService commentLikeService,
            CommentDTOMapper commentDtoMapper,
            PostService postService,
            UserService userService) {
        this.commentService = commentService;
        this.commentLikeService = commentLikeService;
        this.commentDtoMapper = commentDtoMapper;
        this.postService = postService;
        this.userService = userService;
    }

    @ApiOperation(value = "Add comment", notes = "Add new comment")
    @PostMapping(path = "/comment")
    @ResponseBody
    public ResponseEntity<CommentSavedDTO> saveNew(@Valid @RequestBody CommentNewInputDTO commentNewInputDTO) {
        Comment commentParent = null;
        if ((commentNewInputDTO.getCommentParentId() != null) && (commentNewInputDTO.getCommentParentId() != 0)) {
            commentParent = commentService.findCommentById(commentNewInputDTO.getCommentParentId()).orElseThrow(ParentCommentNotFoundException::new);
        }
        Comment comment = commentDtoMapper.fromCommentNewInputDTOtoComment(
                commentNewInputDTO,
                postService.findPostById(commentNewInputDTO.getPostId()).orElseThrow(PostNotFoundException::new),
                userService.findUserById(commentNewInputDTO.getUserOwnerId()).orElseThrow(UserNotFoundException::new),
                commentParent
        );

        Comment commentSaved = commentService.save(comment);
        CommentSavedDTO commentSavedDTO = commentDtoMapper.toCommentSavedDto(commentSaved);
        return new ResponseEntity<>(commentSavedDTO, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Toggle comment like", notes = "Add or delete comment like")
    @PostMapping(path = "/comment/like")
    @ResponseStatus(HttpStatus.CREATED)
    public void toggleCommentLike(@Valid @RequestBody CommentLike commentLike) {
        commentLikeService.toggleCommentLike(commentLike);
    }

    @ApiOperation(value = "Find comments for post", notes = "Find all comments for given post id")
    @GetMapping(path = "/post/{postId}/comment")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Iterable<CommentDTO> findCommentsByPostId(@NotBlank @PathVariable int postId, @RequestParam Integer requestedUserId) {
        return commentService.findCommentsByPostId(postId)
                .parallelStream()
                .map(comment -> {
                    // requestedUserId: temporary before security
                    boolean ifCommentLiked = false;
                    if ((requestedUserId != null) && (requestedUserId != 0)) {
                        ifCommentLiked = commentLikeService.checkIfCommentIsLiked(comment, userService.findUserById(requestedUserId).get());
                    }

                    CommentDTO commentDTO = commentDtoMapper.toCommentDto(
                            comment,
                            commentLikeService.getNumberOfCommentLikes(comment.getId()),
                            commentService.findNumberOfRepliedComments(comment.getId()),
                            ifCommentLiked
                    );
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find sub comments for parent comment", notes = "Find all kids comments for given parent comment id")
    @GetMapping(path = "/comment/{commentParentId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Iterable<CommentDTO> findCommentsByCommentParentId(@PathVariable @NotBlank int commentParentId, @RequestParam Integer requestedUserId) {
        return commentService.findCommentsByCommentParentId(commentParentId)
                .parallelStream()
                .map(comment -> {
                    // requestedUserId: temporary before security
                    boolean ifCommentLiked = false;
                    if ((requestedUserId != null) && (requestedUserId != 0)) {
                        ifCommentLiked = commentLikeService.checkIfCommentIsLiked(comment, userService.findUserById(requestedUserId).get());
                    }

                    CommentDTO commentDTO = commentDtoMapper.toCommentDto(
                            comment,
                            commentLikeService.getNumberOfCommentLikes(comment.getId()),
                            commentService.findNumberOfRepliedComments(comment.getId()),
                            commentLikeService.checkIfCommentIsLiked(comment, userService.findUserById(requestedUserId).get())
                    );
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }
}
