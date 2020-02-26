package com.sda.microblogging.service;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.exception.PostNotFoundException;
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

    @Autowired
    public CommentService(CommentRepository commentRepository, PostService postService){
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    public Comment save(@Valid @NotNull Comment comment) {
        if (!postService.findPostById(comment.getPost().getId()).isPresent()) {
            throw new PostNotFoundException();
        } else {
            return commentRepository.save(comment);
        }
    }

    public List<Comment> findCommentsByPost(int i) {
        return commentRepository.findCommentsByPost(i);
    }

    public Optional<Comment> findCommentById(int commentId) {
        return commentRepository.findById(commentId);
    }

    public List<Comment> findCommentsByCommentParent(int commentParentId){
        return commentRepository.findCommentsByCommentParent(commentParentId);
    }
}
