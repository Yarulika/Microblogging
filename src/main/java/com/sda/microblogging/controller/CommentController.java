package com.sda.microblogging.controller;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.CommentLike;
import com.sda.microblogging.entity.DTO.comment.CommentDTO;
import com.sda.microblogging.entity.DTO.comment.CommentSavedDTO;
import com.sda.microblogging.entity.mapper.CommentDTOMapper;
import com.sda.microblogging.service.CommentLikeService;
import com.sda.microblogging.service.CommentService;
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

    @Autowired
    public CommentController(CommentService commentService, CommentLikeService commentLikeService, CommentDTOMapper commentDtoMapper){
        this.commentService = commentService;
        this.commentLikeService = commentLikeService;
        this.commentDtoMapper = commentDtoMapper;
    }

    @ApiOperation(value = "Add comment", notes = "Add new comment")
    @PostMapping(path = "/comment")
    @ResponseBody
    public ResponseEntity<CommentSavedDTO> saveNew(@Valid @RequestBody Comment comment){
        Comment commentSaved = commentService.save(comment);
        return new ResponseEntity<>(commentDtoMapper.toCommentSavedDto(commentSaved), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Toggle comment", notes = "Add or delete comment like")
    @PostMapping(path="/comment/like")
    @ResponseStatus(HttpStatus.CREATED)
    public void toggleCommentLike(@Valid @RequestBody CommentLike commentLike){
        commentLikeService.toggleCommentLike(commentLike);
    }

    @ApiOperation(value = "Find comments for post", notes = "Find all comments for given post id")
    @GetMapping(path = "/post/{postId}/comment")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<CommentDTO> findCommentsByPostId(@PathVariable @NotBlank int postId){
        return commentService.findCommentsByPostId(postId)
                .parallelStream()
                .map(commentDtoMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find sub comments for parent comment", notes = "Find all kids comments for given parent comment id")
    @GetMapping(path = "/comment/{commentParentId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<CommentDTO> findCommentsByCommentParentId(@PathVariable @NotBlank int commentParentId){
        return commentService.findCommentsByCommentParentId(commentParentId)
                .parallelStream()
                .map(commentDtoMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
