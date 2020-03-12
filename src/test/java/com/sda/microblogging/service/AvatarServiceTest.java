package com.sda.microblogging.service;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvatarServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AvatarService avatarService;

    @Test
    public void saveAvatar_returns_User() throws IOException {
        User user = new User(1, "username", "password", "email@mail.com", true, "some avatar", false, Date.valueOf("2015-01-01"), new Role(2, RoleTitle.USER), null);
        byte[] testImgBytes = "Getting some bytes for image testing".getBytes();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        avatarService.saveAvatar(1, testImgBytes);
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any(User.class));
    }
}