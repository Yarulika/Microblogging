package com.sda.microblogging.controller;

import com.sda.microblogging.entity.DTO.follower.FollowDTO;
import com.sda.microblogging.entity.DTO.follower.FollowerDTO;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.mapper.FollowerDTOMapper;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.service.FollowerService;
import com.sda.microblogging.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/microblogging/v1/follower")
public class FollowerController {

    private FollowerService followerService;
    private FollowerDTOMapper followerDTOMapper;
    private UserService userService;

    @Autowired
    public FollowerController(FollowerService followerService, FollowerDTOMapper followerDTOMapper, UserService userService) {
        this.followerService = followerService;
        this.followerDTOMapper = followerDTOMapper;
        this.userService = userService;
    }

    @ApiOperation(value = "Follow user")
    @PostMapping("/follow")
    @ResponseBody
    public FollowerDTO followUser(@RequestBody @Valid FollowDTO followDTO) {
        Follower follower = followerDTOMapper.fromFollowDTOtoFollower(
                userService.findUserById(followDTO.getFollowingId()).orElseThrow(UserNotFoundException::new),
                userService.findUserById(followDTO.getFollowerId()).orElseThrow(UserNotFoundException::new)
        );
        Follower savedFollower = followerService.followUser(follower);
        return followerDTOMapper.toFollowerDTO(savedFollower);
    }

    @ApiOperation(value = "UnFollow user")
    @PostMapping(value = "/unfollow")
    @ResponseBody
    @Transactional
    public void unFollow(@RequestBody @Valid FollowDTO followDTO) {
        userService.findUserById(followDTO.getFollowingId()).orElseThrow(UserNotFoundException::new);
        userService.findUserById(followDTO.getFollowerId()).orElseThrow(UserNotFoundException::new);
        followerService.unFollowUser(followDTO.getFollowerId(), followDTO.getFollowingId());
    }

    @ApiOperation(value = "Find all followers by username", notes = "Find all followers of user, given his username")
    @GetMapping(path = "/{username}/followers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Iterable<FollowerDTO> findAllFollowersByUsername(@PathVariable @NotBlank String username) {
        int id = userService.findUserByUsername(username).get().getUserId();
        return followerService.getAllFollowersByUserId(id)
                .parallelStream()
                .map(follower -> followerDTOMapper.toFollowerDTO(follower))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find all followings by username", notes = "Find all followings user follows, given his username")
    @GetMapping(path = "/{username}/followings")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Iterable<FollowerDTO> findAllFollowingByFollowerUsername(@PathVariable @NotBlank String username) {

        return followerService.getAllFollowingByFollowerUsername(username)
                .parallelStream()
                .map(follower -> followerDTOMapper.convertFollowerForFollowingDTO(follower))
                .collect(Collectors.toList());
    }
}
