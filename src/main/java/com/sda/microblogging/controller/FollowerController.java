package com.sda.microblogging.controller;

import com.sda.microblogging.entity.DTO.follower.FollowDTO;
import com.sda.microblogging.entity.DTO.follower.FollowerDTO;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.mapper.FollowerDTOMapper;
import com.sda.microblogging.service.FollowerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/microblogging/v1/follower")
public class FollowerController {

    private FollowerService followerService;
    private FollowerDTOMapper followerDTOMapper;

    @Autowired
    public FollowerController(FollowerService followerService,FollowerDTOMapper followerDTOMapper) {
        this.followerService = followerService;
        this.followerDTOMapper=followerDTOMapper;
    }


    @ApiOperation(value = "Follow user")
    @PostMapping("/follow")
    @ResponseBody
    public FollowerDTO followUser(@RequestBody @Valid FollowDTO followDTO){
        return followerDTOMapper.toFollowerDTO(followerService.followUser(followDTO));
    }

    @ApiOperation(value = "UnFollow user")
    @PostMapping(value = "/unfollow")
    @ResponseBody
    @Transactional
    public void unFollow(@RequestBody @Valid FollowDTO followDTO){
        followerService.unFollowUser(followDTO.getUserId(),followDTO.getFollowingId());
    }

}
