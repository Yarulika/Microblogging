package com.sda.microblogging.exception;

public class UserDetailsFoundException extends RuntimeException {

    public UserDetailsFoundException(String msg) {
        super(msg);
    }
}
