package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User email or password were not found")
public class InvalidEmailOrPasswordException extends RuntimeException {

    @Override
    public String getMessage() {
        return "User email or password were not found";
    }
}
