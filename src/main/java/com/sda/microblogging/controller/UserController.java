package com.sda.microblogging.controller;

import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.FollowerService;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;
    private final FollowerService followerService;

    @Autowired
    public UserController(UserService userService, FollowerService followerService) {
        this.userService = userService;
        this.followerService = followerService;
    }

    @GetMapping(path = "/user/allActive")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<User> findAllActiveUsers() {
        return userService.findAllActiveUsers();
    }

    @GetMapping(path = "/user/{username}/followers")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<Follower> findAllFollowersByUsername(@PathVariable @NotBlank String username){
        int id = userService.findUserByUsername(username).get().getUserId();
        return followerService.getAllFollowersByUserId(id);
    }

    @GetMapping(path = "/user/{username}/followings")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<Follower> findAllFollowingByFollowerUsername(@PathVariable @NotBlank String username){
        int id = userService.findUserByUsername(username).get().getUserId();
        return followerService.getAllFollowingByFollowerId(id);
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
    public ResponseEntity<User> saveNew(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

}
