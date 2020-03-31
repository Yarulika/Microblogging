package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Avatar was not found")
public class AvatarNotExistsException extends RuntimeException {

    @Override
    public String getMessage(){
        return "Avatar was not found: please check URL";
    }
}
