package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post was not found")
public class PostNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Provided Post was not found";
    }
}
