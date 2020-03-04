package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Parent comment was not found")
public class ParentCommentNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Provided parent comment was not found";
    }
}
