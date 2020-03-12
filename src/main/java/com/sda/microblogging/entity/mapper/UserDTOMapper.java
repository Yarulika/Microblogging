package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.DTO.user.UserDTO;
import com.sda.microblogging.entity.DTO.user.UserSignUpDTO;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class UserDTOMapper {

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
                .title(user.getRole().getTitle())
                .numberOfFollowers(numberOfFollowers)
                .numberOfFollowing(numberOfFollowing)
                .build();
    }

    public User fromUserSignUpDTOtoUser(UserSignUpDTO userSignUpDTO){
        long millis = System.currentTimeMillis();
        Date creationDate = new Date(millis);

        return User.builder()
                .username(userSignUpDTO.getUsername())
                .password(userSignUpDTO.getPassword())
                .email(userSignUpDTO.getEmail())
                .isPrivate(true)
                .avatar("default cool I")
                .isBlocked(false)
                .creationDate(creationDate)
                .role(new Role(2, RoleTitle.USER))
                .build();
    }
}
