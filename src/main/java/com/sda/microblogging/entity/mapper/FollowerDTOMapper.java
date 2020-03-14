package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.DTO.follower.FollowDTO;
import com.sda.microblogging.entity.DTO.follower.FollowerDTO;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.service.FollowerService;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowerDTOMapper {

    @Autowired
    UserService userService;
    @Autowired
    FollowerService followerService;

    public FollowerDTO toFollowerDTO(Follower follower) {

        return FollowerDTO.builder()
                .id(follower.getId())
                .followerId(follower.getFollower().getUserId())
                .username(follower.getFollower().getUsername())
                .avatar(follower.getFollower().getAvatar())
                .date(follower.getFollowingDate())
                .build();
    }
}
