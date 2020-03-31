package com.sda.microblogging.service;

import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.AvatarNotExistsException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.io.*;

@Service
public class AvatarService {
    @Value("${avatars.base.get.url}")
    private String avatarsBaseGetURL;
    @Value("${avatars.base.path}")
    private String avatarsBasePath;

    private UserRepository userRepository;

    @Autowired
    public AvatarService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String saveAvatar(@NotBlank Integer userId, byte[] imageData) throws IOException {
        // TODO: will implement later saving img on some media server

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        String avatarPath = getAvatarPath(user.getUsername());

        FileOutputStream fos = null;
        try {
            File avatar = new File(avatarPath);
            fos = new FileOutputStream(avatar);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(imageData);
            baos.writeTo(fos);
            user.setAvatar(avatarPath);
            userRepository.save(user);
            return avatarsBaseGetURL + avatar.getAbsolutePath();
        } catch (IOException ioe) {
            System.err.println("Problem writing to the file " + avatarPath);
            ioe.printStackTrace();
            throw ioe;
        } finally {
            fos.close();
        }
    }

    public String getAvatarPath(String username) {
        return avatarsBasePath + File.separator + username + ".jpeg";
    }

    public InputStream getAvatar(String imageURl) {
        try {
            FileInputStream image = new FileInputStream(imageURl);
            return image;
        } catch (FileNotFoundException ex) {
            throw new AvatarNotExistsException();
        }
    }
}
