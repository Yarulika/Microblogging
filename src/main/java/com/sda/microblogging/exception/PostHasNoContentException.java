package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Post has no content")
public class PostHasNoContentException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Post has no content";
    }
}
