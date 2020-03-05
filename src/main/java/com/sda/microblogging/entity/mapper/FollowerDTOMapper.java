package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.DTO.follower.FollowerDTO;
import com.sda.microblogging.entity.Follower;
import org.springframework.stereotype.Component;

@Component
public class FollowerDTOMapper {

    public FollowerDTO toFollowerDTO(Follower follower) {

        return FollowerDTO.builder()
                .id(follower.getId())
                .userId(follower.getUser().getUserId())
                .userUsername(follower.getUser().getUsername())
                .userAvatar(follower.getUser().getAvatar())
                .followerId(follower.getFollower().getUserId())
                .followerUsername(follower.getFollower().getUsername())
                .followerAvatar(follower.getFollower().getAvatar())
                .followingDate(follower.getFollowingDate())
                .build();
    }
}
