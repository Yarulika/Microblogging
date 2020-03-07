package com.sda.microblogging.entity.DTO.user;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpDTO {

    @NotNull
    @Size(min = 1, max = 45)
    private String username;

    @NotNull
    @Size(min = 1, max = 45)
    private String email;

    @NotNull
    @Size(min = 1, max = 45)
    private String password;
}
