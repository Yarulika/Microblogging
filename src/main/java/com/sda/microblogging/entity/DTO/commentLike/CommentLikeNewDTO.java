package com.sda.microblogging.entity.DTO.commentLike;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class CommentLikeNewDTO {

    @NotNull
    @Positive
    private int commentId;

    @NotNull
    @Positive
    private int userId;
}
