package com.sda.microblogging.entity.DTO.post;

import com.sda.microblogging.common.RoleTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class PostShareDTO extends PostDTO {

    OriginalPostDTO originalPostDTO;

    public PostShareDTO(String username, int userId, boolean isUserPrivate, RoleTitle userRole, String avatar, int numberOfPostShares, int postId, String content, boolean isPostEdited, Date postCreatedDate, int numberOfPostLikes, int numberOfComments, boolean isPostLiked) {
        super(username, userId, isUserPrivate, userRole, avatar, numberOfPostShares, postId, content, isPostEdited, postCreatedDate, numberOfPostLikes, numberOfComments, isPostLiked);
    }
}
