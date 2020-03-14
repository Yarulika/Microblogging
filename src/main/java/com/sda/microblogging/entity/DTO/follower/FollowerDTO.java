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
    private String username;
    private String avatar;
    private Date date;
}
