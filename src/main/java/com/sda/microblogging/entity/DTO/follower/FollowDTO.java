package com.sda.microblogging.entity.DTO.follower;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class FollowDTO {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer followingId;
    @NotNull
    private Date followingDate;
}
