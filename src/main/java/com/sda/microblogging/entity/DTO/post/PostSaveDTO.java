package com.sda.microblogging.entity.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveDTO {

    private String content;
    private Boolean isEdited;
    @NotNull
    private int owner;
    private Date creationDate;
    private Integer originalPost;
}
