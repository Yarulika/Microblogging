package com.sda.microblogging.entity.DTO.follower;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowDTO {
    @NotNull
    private Integer followerId;
    @NotNull
    private Integer followingId;
}
