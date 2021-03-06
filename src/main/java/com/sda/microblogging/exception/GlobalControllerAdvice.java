package com.sda.microblogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(CommentNotFoundException.class)
    public void handleCommentNotFoundException(CommentNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

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

    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(UserNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler
    public void handlePostContentNotFoundException(PostContentNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyFollowedException.class)
    public void handleUserAlreadyFollowedException(UserAlreadyFollowedException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyNotFollowedException.class)
    public void handleUserAlreadyNotFollowedException(UserAlreadyNotFollowedException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(AvatarNotExistsException.class)
    public void handleAvatarNotExistsException(AvatarNotExistsException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
