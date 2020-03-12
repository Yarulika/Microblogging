package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Post content should not be null")
public class PostContentNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Post content was null, you should write something, max 150 characters";
    }
}
