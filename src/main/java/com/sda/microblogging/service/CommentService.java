package com.sda.microblogging.service;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.exception.ParentCommentNotFoundException;
import com.sda.microblogging.exception.PostNotFoundException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostService postService;
    private UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService){
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Comment save(@Valid @NotNull Comment comment) {
        if (!postService.findPostById(comment.getPost().getId()).isPresent()) {
            throw new PostNotFoundException();
        }
        else if (!userService.findUserById(comment.getOwner().getUserId()).isPresent()){
            throw new UserNotFoundException();
        } else {
            return commentRepository.save(comment);
        }
    }

    public Optional<Comment> findCommentById(int commentId) {
        return commentRepository.findById(commentId);
    }

    public List<Comment> findCommentsByPostId(int postId) {
        if (!postService.findPostById(postId).isPresent()){
            throw new PostNotFoundException();
        } else {
        return commentRepository.findCommentsByPostId(postId);
        }
    }

    public List<Comment> findCommentsByCommentParentId(int commentParentId){
        if (!commentRepository.findById(commentParentId).isPresent()) {
            throw new ParentCommentNotFoundException();
        }
        return commentRepository.findCommentsByCommentParentId(commentParentId);
    }
}
