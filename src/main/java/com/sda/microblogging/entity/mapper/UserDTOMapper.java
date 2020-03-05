package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.DTO.user.UserDTO;
import com.sda.microblogging.entity.DTO.user.UserSavedDTO;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public UserSavedDTO toUserSavedDto(User user){
        if (user.getUserId() == null) {
            throw new UserNotFoundException();
        }
        return UserSavedDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .creationDate(user.getCreationDate())
                .role(user.getRole())
                .build();
    }

    public UserDTO toUserDto(User user, int numberOfFollowers, int numberOfFollowing){
        if (user.getUserId() == null) {
            throw new UserNotFoundException();
        }
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isPrivate(user.isPrivate())
                .avatar(user.getAvatar())
                .isBlocked(user.isBlocked())
                .creationDate(user.getCreationDate())
                .role(user.getRole())
                .numberOfFollowers(numberOfFollowers)
                .numberOfFollowing(numberOfFollowing)
                .build();
    }
}
