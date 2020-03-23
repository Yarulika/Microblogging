package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.DTO.follower.FollowerDTO;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.FollowerService;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

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

    public FollowerDTO convertFollowerForFollowingDTO(Follower follower) {

        return FollowerDTO.builder()
                .id(follower.getId())
                .followerId(follower.getUser().getUserId())
                .username(follower.getUser().getUsername())
                .avatar(follower.getUser().getAvatar())
                .date(follower.getFollowingDate())
                .build();
    }

    public Follower fromFollowDTOtoFollower(User followingUser, User followerUser){
        long millis = System.currentTimeMillis();
        Date creationDate = new Date(millis);

        return Follower.builder()
                .user(followingUser)
                .follower(followerUser)
                .followingDate(creationDate)
                .build();
    }
}
