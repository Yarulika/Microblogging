package com.sda.microblogging.controller;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping(path = "/microblogging/v1")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @ApiOperation(value = "Add comment", notes = "Add new comment")
    @PostMapping(path = "/comment")
    @ResponseBody
    public ResponseEntity<Comment> saveNew(@Valid @RequestBody Comment comment){
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Find comments for post", notes = "Find all comments for given post id")
    @GetMapping(path = "/post/{postId}/comment")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<Comment> findCommentsByPostId(@PathVariable @NotBlank int postId){
        return commentService.findCommentsByPostId(postId);
    }

    @ApiOperation(value = "Find sub comments for parent comment", notes = "Find all kids comments for given parent comment id")
    @GetMapping(path = "/comment/{commentParentId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<Comment> findCommentsByCommentParentId(@PathVariable @NotBlank int commentParentId){
        return commentService.findCommentsByCommentParentId(commentParentId);
    }
}
