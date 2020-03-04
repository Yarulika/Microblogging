package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.DTO.comment.CommentDTO;
import com.sda.microblogging.entity.DTO.comment.CommentSavedDTO;
import com.sda.microblogging.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentDTOMapper {

    @Autowired
    CommentLikeService commentLikeService;

    public CommentDTO toCommentDto(Comment comment){
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
                .numberOfCommentLikes(commentLikeService.getNumberOfCommentLikes(comment.getId()))
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
}
