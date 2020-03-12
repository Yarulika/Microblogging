package com.sda.microblogging.entity.DTO.comment;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private int id;
    private String content;
    private int postId;
    private int userOwnerId;
    private String userOwnerUsername;
    private Date creationDate;
    private Integer commentParentId;
    private int numberOfCommentLikes;
    private int numberOfCommentShares;
    private List<CommentDTO> subComments;
}
