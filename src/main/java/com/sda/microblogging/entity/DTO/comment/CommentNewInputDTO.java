package com.sda.microblogging.entity.DTO.comment;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentNewInputDTO {

    @NotNull
    private String content;

    @NotNull
    @Positive
    private int postId;

    @NotNull
    @Positive
    private int userOwnerId;

    private Integer commentParentId;
}
