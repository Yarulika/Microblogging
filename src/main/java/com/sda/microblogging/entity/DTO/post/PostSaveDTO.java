package com.sda.microblogging.entity.DTO.post;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class PostSaveDTO {

    private String content;
    private Boolean isEdited;
    private User owner;
    private Date creationDate;
    private Post originalPost;
}
