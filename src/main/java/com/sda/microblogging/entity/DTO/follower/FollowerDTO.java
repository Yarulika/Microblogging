package com.sda.microblogging.entity.DTO.follower;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowerDTO {
    private Integer id;
    private Integer followerId;
    private String followerUsername;
    private String followerAvatar;
    private Date followingDate;
}
