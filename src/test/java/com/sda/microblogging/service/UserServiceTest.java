package com.sda.microblogging.service;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.UserRepository;
import org.junit.Before;
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

    @Test
    public void save_returns_saved_user() throws Exception{
        Role role = new Role();
        role.setId(1);
        role.setTitle(RoleTitle.ADMIN);

        User expectedUser = new User();
        expectedUser.setUserId(1);
        expectedUser.setUsername("name");
        expectedUser.setPassword("password");
        expectedUser.setEmail("name@mail.com");
        expectedUser.setPrivate(false);
        expectedUser.setAvatar("avatar");
        expectedUser.setCreationDate(Date.valueOf("2020-01-01"));
        expectedUser.setRole(role);

        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        User actualUser = userService.save(expectedUser);

        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test
    public void findUserByUsername_with_given_correct_username_OK() {
        String testUsername = "testUsername";
        User expectedUser = new User(null, "testUsername", "password", "email", true, null, false, null, null, null, null);
        when(userRepository.findByUsername(testUsername)).thenReturn(expectedUser);

        User actualUser = userService.findUserByUsername(testUsername).get();
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test
    public void getById_existing_user_OK() throws Exception{
        int testId = 100;
        User expectedUser = new User(testId, "testUsername", "password", "email", true, null, false, null, null, null, null);

        when(userRepository.findById(testId)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.getById(testId).orElseThrow(Exception::new);

        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

}