package com.sda.microblogging.entity.DTO.post;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@Builder
public class OriginalPostDTO {
    Integer userId;
    Integer postID;
    String username;
    String avatar;
    String content;
    Date postCreatedDate;
}