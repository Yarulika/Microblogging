package com.sda.microblogging.entity.dto.post;

import com.sda.microblogging.common.RoleTitle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private String username;
    private int userId;
    private boolean isUserPrivate;
    private RoleTitle userRole;
    private String avatar;
    private int numberOfPostShares;
    private int postId;
    private String content;
    private boolean isPostEdited;
    private Date postCreatedDate;
    private int numberOfPostLikes;
    private int numberOfComments;
}
