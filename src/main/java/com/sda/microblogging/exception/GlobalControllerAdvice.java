package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(InvalidEmailOrPasswordException.class)
    public void handleInvalidEmailOrPasswordException(InvalidEmailOrPasswordException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(ParentCommentNotFoundException.class)
    public void handleParentCommentNotFoundException(ParentCommentNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public void handlePostNotFoundException(PostNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(UserDetailsFoundException.class)
    public void handleUserDetailsFoundException(UserDetailsFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyHasRequestedPrivacyException.class)
    public void handleUserAlreadyHasRequestedPrivacyException(UserAlreadyHasRequestedPrivacyException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(UserNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler
    public void handlePostContentNotFoundException(PostContentNotFoundException exception,HttpServletResponse response)throws  IOException{
        response.sendError(HttpStatus.BAD_REQUEST.value(),exception.getMessage());
    }
}
