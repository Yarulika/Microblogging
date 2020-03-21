package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already followed")
public class UserAlreadyFollowedException extends RuntimeException {

    @Override
    public String getMessage() {
        return "User already followed";
    }
}
