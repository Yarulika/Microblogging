package com.sda.microblogging.entity.DTO.comment;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentSavedDTO {
    private int id;
    private String content;
    private int postId;
    private int userOwnerId;
    private Date creationDate;
}
