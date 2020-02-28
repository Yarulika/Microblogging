package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User was not found")
public class UserNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Given User was not found";
    }
}