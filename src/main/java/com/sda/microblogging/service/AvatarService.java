package com.sda.microblogging.service;

import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class AvatarService {

    private UserRepository userRepository;

    @Autowired
    public AvatarService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveAvatar(@NotBlank Integer userId, byte[] imageData) throws IOException {
        // TODO: will implement later saving img on some media server
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }  {
            FileOutputStream fos = null;
            String imgPath = "src/main/resources/static/avatar/" + user.get().getUsername() + ".jpg";
            String imgPathForShare = "/avatar/" + user.get().getUsername() + ".jpg";

            try {
                fos = new FileOutputStream(new File(imgPath));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos.write(imageData);
                baos.writeTo(fos);

                user.get().setAvatar(imgPathForShare);
                userRepository.save(user.get());
                return user.get();

            } catch (IOException ioe) {
                System.err.println("Problem writing to the file " + imgPath);
                ioe.printStackTrace();
                throw ioe;
            } finally {
                fos.close();
            }
        }
    }
}
