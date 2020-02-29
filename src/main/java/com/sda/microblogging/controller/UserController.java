package com.sda.microblogging.controller;

import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.FollowerService;
import com.sda.microblogging.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping(path = "/microblogging/v1/user")
public class UserController {

    private final UserService userService;
    private final FollowerService followerService;

    @Autowired
    public UserController(UserService userService, FollowerService followerService) {
        this.userService = userService;
        this.followerService = followerService;
    }

    @ApiOperation(value = "Get all active users", notes = "Get all active users (IsBlocked = false)")
    @GetMapping(path = "/allActive")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<User> findAllActiveUsers() {
        return userService.findAllActiveUsers();
    }

    @ApiOperation(value = "Find all followers by username", notes = "Find all followers of user, given his username")
    @GetMapping(path = "/{username}/followers")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<Follower> findAllFollowersByUsername(@PathVariable @NotBlank String username){
        int id = userService.findUserByUsername(username).get().getUserId();
        return followerService.getAllFollowersByUserId(id);
    }

    @ApiOperation(value = "Find all followings", notes = "Find all followings user follows, given his username")
    @GetMapping(path = "/{username}/followings")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<Follower> findAllFollowingByFollowerUsername(@PathVariable @NotBlank String username){
        int id = userService.findUserByUsername(username).get().getUserId();
        return followerService.getAllFollowingByFollowerId(id);
    }

    @ApiOperation(value = "Find user by username", notes = "Find user by username")
    @GetMapping(path = "/{username}")
    @ResponseBody
    public ResponseEntity<User> getUserByUsername(@PathVariable @NotNull String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Find user by email", notes = "Find user by email address")
    @GetMapping(path = "/byEmail/{email}")
    @ResponseBody
    public ResponseEntity<User> getUserByEmail(@PathVariable @NotNull String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Save new user", notes = "Save new user")
    @PostMapping
    @ResponseBody
    public ResponseEntity<User> saveNew(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

}
