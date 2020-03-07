package com.sda.microblogging.entity.DTO.comment;

import lombok.*;

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
}
