package com.sda.microblogging.service;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.CommentLike;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class CommentLikeService {
    @Autowired
    CommentLikeRepository commentLikeRepository;


    public void toggleCommentLike(@NotNull CommentLike commentLike) {
        if (!commentLikeRepository.findByCommentAndUser(commentLike.getComment(),commentLike.getUser()).isPresent()){
            commentLikeRepository.save(commentLike);
        }else
            commentLikeRepository.delete(commentLike);
    }


    public int getNumberOfCommentLikes(@NotNull int commentId){
        return commentLikeRepository.countByCommentId(commentId);
    }
}
