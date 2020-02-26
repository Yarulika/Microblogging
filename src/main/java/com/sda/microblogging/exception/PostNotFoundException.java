package com.sda.microblogging.exception;

public class PostNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Provided Post was not found";
    }
}
