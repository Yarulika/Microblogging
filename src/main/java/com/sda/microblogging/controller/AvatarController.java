package com.sda.microblogging.controller;

import com.sda.microblogging.entity.DTO.user.UserDTO;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.entity.mapper.UserDTOMapper;
import com.sda.microblogging.service.AvatarService;
import com.sda.microblogging.service.FollowerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/microblogging/v1/user")
public class AvatarController {

    private AvatarService avatarService;
    private FollowerService followerService;
    private UserDTOMapper userDTOMapper;

    @Autowired
    public AvatarController(AvatarService avatarService, FollowerService followerService, UserDTOMapper userDTOMapper) {
        this.avatarService = avatarService;
        this.followerService = followerService;
        this.userDTOMapper = userDTOMapper;
    }

    @ApiOperation(value = "Upload avatar", notes = "Save avatar image")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/uploadAvatar")
    public ResponseEntity<UserDTO> saveAvatar(
                @RequestParam("userId") int userId,
                @RequestParam("avatar") MultipartFile avatarFile ) throws IOException {

        byte[] avatarFileBytes = avatarFile.getBytes();
        User userWithAvatar = avatarService.saveAvatar(userId, avatarFileBytes);

        UserDTO userDTOWithAvatar = userDTOMapper.toUserDto(
                userWithAvatar,
                followerService.countFollowersByUserId(userWithAvatar.getUserId()),
                followerService.countFollowingByFollowerId(userWithAvatar.getUserId()));
        return new ResponseEntity<>(userDTOWithAvatar, HttpStatus.CREATED);
    }
}
