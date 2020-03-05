package com.sda.microblogging.controller;

import com.sda.microblogging.entity.DTO.follower.FollowerDTO;
import com.sda.microblogging.entity.DTO.user.UserDTO;
import com.sda.microblogging.entity.DTO.user.UserSavedDTO;
import com.sda.microblogging.entity.DTO.user.UserSignUpDTO;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.entity.mapper.FollowerDTOMapper;
import com.sda.microblogging.entity.mapper.UserDTOMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/microblogging/v1/user")
public class UserController {

    private UserService userService;
    private FollowerService followerService;
    private UserDTOMapper userDTOMapper;
    private FollowerDTOMapper followerDTOMapper;

    @Autowired
    public UserController(UserService userService, FollowerService followerService, UserDTOMapper userDTOMapper, FollowerDTOMapper followerDTOMapper) {
        this.userService = userService;
        this.followerService = followerService;
        this.userDTOMapper = userDTOMapper;
        this.followerDTOMapper = followerDTOMapper;
    }

    @ApiOperation(value = "Get all active users", notes = "Get all active users (IsBlocked = false)")
    @GetMapping(path = "/allActive")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<UserDTO> findAllActiveUsers() {
        return userService.findAllActiveUsers()
                .parallelStream()
                .map(user -> {
                    UserDTO userDTO = userDTOMapper.toUserDto(
                            user,
                            followerService.countFollowersByUserId(user.getUserId()),
                            followerService.countFollowingByFollowerId(user.getUserId()));
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find all followers by username", notes = "Find all followers of user, given his username")
    @GetMapping(path = "/{username}/followers")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<FollowerDTO> findAllFollowersByUsername(@PathVariable @NotBlank String username){
        int id = userService.findUserByUsername(username).get().getUserId();
        return followerService.getAllFollowersByUserId(id)
                .parallelStream()
                .map(follower -> followerDTOMapper.toFollowerDTO(follower))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find all followings", notes = "Find all followings user follows, given his username")
    @GetMapping(path = "/{username}/followings")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Iterable<FollowerDTO> findAllFollowingByFollowerUsername(@PathVariable @NotBlank String username){
        int id = userService.findUserByUsername(username).get().getUserId();
        return followerService.getAllFollowingByFollowerId(id)
                .parallelStream()
                .map(follower -> followerDTOMapper.toFollowerDTO(follower))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find user by username", notes = "Find user by username")
    @GetMapping(path = "/{username}")
    @ResponseBody
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable @NotNull String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            UserDTO userDTO = userDTOMapper.toUserDto(
                    user.get(),
                    followerService.countFollowersByUserId(user.get().getUserId()),
                    followerService.countFollowingByFollowerId(user.get().getUserId()));
            return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Find user by email", notes = "Find user by email address")
    @GetMapping(path = "/byEmail/{email}")
    @ResponseBody
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable @NotNull String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isPresent()) {
            UserDTO userDTO = userDTOMapper.toUserDto(
                    user.get(),
                    followerService.countFollowersByUserId(user.get().getUserId()),
                    followerService.countFollowingByFollowerId(user.get().getUserId()));
            return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Sign up new user", notes = "Saves new user - returns UserSavedDTO with minimum info")
    @PostMapping(path="/signUp")
    @ResponseBody
    public ResponseEntity<UserSavedDTO> signUp(@Valid @RequestBody UserSignUpDTO userSignUpDTO) {
        User user = userDTOMapper.fromUserSignUpDTOtoUser(userSignUpDTO);
        User savedUser = userService.save(user);
        UserSavedDTO userSavedDTO = userDTOMapper.toUserSavedDto(savedUser);
        return new ResponseEntity<>(userSavedDTO, HttpStatus.CREATED);
    }
}
