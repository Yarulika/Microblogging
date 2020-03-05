package com.sda.microblogging.entity.DTO.user;

import com.sda.microblogging.entity.Role;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSavedDTO {
    private Integer userId;
    private String username;
    private String email;
    private Date creationDate;
    private Role role;
}
