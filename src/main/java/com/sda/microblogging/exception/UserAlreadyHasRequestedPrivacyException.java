package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User already has this privacy")
public class UserAlreadyHasRequestedPrivacyException extends RuntimeException {

    @Override
    public String getMessage() {
        return "User already has this privacy";
    }
}
