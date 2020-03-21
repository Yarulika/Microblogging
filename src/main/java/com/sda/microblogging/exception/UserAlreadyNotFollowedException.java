package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User already not followed")
public class UserAlreadyNotFollowedException extends RuntimeException {

    @Override
    public String getMessage(){
        return "User has been already unfollowed";
    }
}
