package com.sda.microblogging.controller;

import com.sda.microblogging.service.AvatarService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(path = "/microblogging/v1/user")
public class AvatarController {

    private AvatarService avatarService;

    @Autowired
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @ApiOperation(value = "Upload avatar", notes = "Save avatar image")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/uploadAvatar")
    public ResponseEntity<String> saveAvatar(
            @RequestParam("userId") int userId,
            @RequestParam("avatar") MultipartFile avatarFile) throws IOException {

        byte[] avatarFileBytes = avatarFile.getBytes();
        String avatarURL = avatarService.saveAvatar(userId, avatarFileBytes);
        return new ResponseEntity<>(avatarURL, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get avatar", notes = "Get saved avatar")
    @GetMapping(path = "/avatar")
    public ResponseEntity<byte[]> getAvatar(@RequestParam String avatarPath) throws IOException {
        InputStream avatarInputStream = avatarService.getAvatar(avatarPath);
        byte[] bytes = StreamUtils.copyToByteArray(avatarInputStream);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}
