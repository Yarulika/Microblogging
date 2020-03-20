package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Comment was not found")
public class CommentNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Provided comment was not found";
    }
}
