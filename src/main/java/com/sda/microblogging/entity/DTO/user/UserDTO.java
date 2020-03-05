package com.sda.microblogging.entity.DTO.user;

import com.sda.microblogging.entity.Role;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer userId;
    private String username;
    private String email;
    private boolean isPrivate;
    private String avatar;
    private boolean isBlocked;
    private Date creationDate;
    private Role role;
    private int numberOfFollowers;
    private int numberOfFollowing;
}
