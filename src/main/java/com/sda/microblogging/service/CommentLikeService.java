package com.sda.microblogging.service;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.CommentLike;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class CommentLikeService {

    private CommentLikeRepository commentLikeRepository;

    @Autowired
    public CommentLikeService(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    public void toggleCommentLike(@NotNull CommentLike commentLike) {
        Optional<CommentLike> actualCommentLike = commentLikeRepository.findByCommentAndUser(commentLike.getComment(), commentLike.getUser());
        if (!actualCommentLike.isPresent()) {
            commentLikeRepository.save(commentLike);
        } else {
            commentLikeRepository.delete(actualCommentLike.get());
        }
    }

    public int getNumberOfCommentLikes(@NotNull int commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }
}
