package com.sda.microblogging.controller;

import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users/{username}")
    @ResponseBody
    public User getUserByUsername(@PathVariable String username){
        return userService.findUserByUsername(username).get();
    }
}