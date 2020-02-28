package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User details were not found")
public class UserDetailsFoundException extends RuntimeException {

    public UserDetailsFoundException(String msg) {
        super(msg);
    }
}
