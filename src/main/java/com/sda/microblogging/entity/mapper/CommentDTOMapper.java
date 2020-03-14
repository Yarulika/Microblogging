package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.DTO.comment.CommentDTO;
import com.sda.microblogging.entity.DTO.comment.CommentNewInputDTO;
import com.sda.microblogging.entity.DTO.comment.CommentSavedDTO;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.CommentService;
import com.sda.microblogging.service.PostService;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Component
public class CommentDTOMapper {

    public CommentDTO toCommentDto(Comment comment, int numberCommentLikes, int numberCommentShares, boolean ifCommentLiked){
        Integer commentParentID = null;
        if (comment.getCommentParent() != null) {
            commentParentID = comment.getCommentParent().getId();
        }

        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .userOwnerId(comment.getOwner().getUserId())
                .userOwnerUsername(comment.getOwner().getUsername())
                .creationDate(comment.getCreationDate())
                .commentParentId(commentParentID)
                .numberOfCommentLikes(numberCommentLikes)
                .numberOfCommentShares(numberCommentShares)
                .isCommentLiked(ifCommentLiked)
                .build();
    }

    public CommentSavedDTO toCommentSavedDto(Comment comment){
        return CommentSavedDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .userOwnerId(comment.getOwner().getUserId())
                .build();
    }

    public Comment fromCommentNewInputDTOtoComment(CommentNewInputDTO commentNewInputDTO, Post post, User owner, Comment commentParent){
        long millis = System.currentTimeMillis();
        Date creationDate = new Date(millis);

        return Comment.builder()
                .content(commentNewInputDTO.getContent())
                .post(post)
                .owner(owner)
                .creationDate(creationDate)
                .commentParent(commentParent)
                .build();
    }
}
