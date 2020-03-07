package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.DTO.comment.CommentDTO;
import com.sda.microblogging.entity.DTO.comment.CommentSavedDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentDTOMapper {

    public CommentDTO toCommentDto(Comment comment, int numberCommentLikes, int numberCommentShares){
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
