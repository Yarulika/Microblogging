package com.sda.microblogging.entity.DTO.user;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {

    @NotNull
    @Size(min = 1, max = 45)
    private String email;

    @NotNull
    @Size(min = 1, max = 45)
    private String password;
}
