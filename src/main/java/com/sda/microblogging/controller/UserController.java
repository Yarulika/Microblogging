package com.sda.microblogging.controller;

import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.IncorrectUserDetailsException;
import com.sda.microblogging.exception.UserDetailsFoundException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user/allActive")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<User> findAllActiveUsers() {
        return userService.findAllActiveUsers();
    }

    @GetMapping(path = "/user/{username}")
    @ResponseBody
    public ResponseEntity<User> getUserByUsername(@PathVariable @NotNull String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/user/byEmail/{email}")
    @ResponseBody
    public ResponseEntity<User> getUserByEmail(@PathVariable @NotNull String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/user")
    @ResponseBody
    public ResponseEntity<?> saveNew(@Valid @RequestBody User user, BindingResult bindingResult) {
        // TODO :(((
        if (bindingResult.hasErrors()) {
            throw new IncorrectUserDetailsException(bindingResult);
        } else {
            try {
                userService.save(user);
                return new ResponseEntity<>((user), HttpStatus.CREATED);
            } catch (UserDetailsFoundException ex) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
        }
    }

}


//USER:
//        TODO "/{username}/followers" => getAllFollowers
//        TODO "/{username}/following" => getAllFollowings
//        "/login" => POST login
//        "/password" PUT
