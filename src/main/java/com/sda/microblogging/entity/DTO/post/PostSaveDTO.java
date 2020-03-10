package com.sda.microblogging.entity.DTO.post;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
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
    private User owner;
    private Date creationDate;
    private Post originalPost;
}
