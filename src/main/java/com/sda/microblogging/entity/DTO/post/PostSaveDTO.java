package com.sda.microblogging.entity.DTO.post;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSaveDTO {

    private String content;
    private Boolean isEdited;
    @NotNull
    private User owner;
    private Date creationDate;
    private Post originalPost;
}
