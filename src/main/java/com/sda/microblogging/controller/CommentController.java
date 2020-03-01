package com.sda.microblogging.controller;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/microblogging/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @ApiOperation(value = "Add comment", notes = "Add comment")
    @PostMapping
    @ResponseBody
    public ResponseEntity<Comment> saveNew(@Valid @RequestBody Comment comment){
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }

    //Get all comments for post
//    GET micr../v1/post/{postId}/comment/

    //Get all subcomments
//    GET micr../v1/comment/{comment_id}

}
