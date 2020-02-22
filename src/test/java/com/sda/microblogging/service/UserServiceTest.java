package com.sda.microblogging.service;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User expectedUser;

    @BeforeEach
    public void init(){
        expectedUser = new User();
        expectedUser.setUserId(1);
        expectedUser.setUsername("name");
        expectedUser.setPassword("password");
        expectedUser.setEmail("name@mail.com");
        expectedUser.setPrivate(false);
        expectedUser.setAvatar("avatar");
        expectedUser.setBlocked(false);
        expectedUser.setCreationDate(Date.valueOf("2020-01-01"));
        Role role = new Role(1, RoleTitle.ADMIN);
        expectedUser.setRole(role);
        expectedUser.setBlockedUsers(null);
        expectedUser.setFollowers(null);
    }

    @Test
    public void save_returns_saved_user(){
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        User actualUser = userService.save(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findUserByUsername_with_given_correct_username_OK() {
        String testUsername = expectedUser.getUsername();
        when(userRepository.findByUsername(testUsername)).thenReturn(expectedUser);
        User actualUser = userService.findUserByUsername(testUsername).get();
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test
    public void getById_with_given_correct_id_OK() throws Exception{
        int testId = expectedUser.getUserId();
        when(userRepository.findById(testId)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.getById(testId).orElseThrow(Exception::new);
        assertEquals(expectedUser, actualUser);
    }
}
