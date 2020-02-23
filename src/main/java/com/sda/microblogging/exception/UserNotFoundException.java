package com.sda.microblogging.exception;

public class UserNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Given User was not found";
    }
}