package com.sda.microblogging.entity.DTO.user;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UserPasswordDTO {

    @NotNull
    private String oldPassword;

    @NotNull
    @Size(min = 1, max = 45)
    private String newPassword;
}
