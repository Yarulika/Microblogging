package com.sda.microblogging.service;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.UserDetailsFoundException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User expectedUser;

    @BeforeEach
    public void init() {
        expectedUser = new User();
        expectedUser.setUserId(1);
        expectedUser.setUsername("usname");
        expectedUser.setPassword("password");
        expectedUser.setEmail("email@mail.com");
        expectedUser.setPrivate(false);
        expectedUser.setAvatar("avatar");
        expectedUser.setBlocked(false);
        expectedUser.setCreationDate(Date.valueOf("2020-01-01"));
        Role role = new Role(1, RoleTitle.ADMIN);
        expectedUser.setRole(role);
        expectedUser.setBlockedUsers(null);
    }

    @Test
    public void save_returns_user_if_his_details_were_not_found() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        userService.save(expectedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void save_with_existing_username_returns_UserDetailsFoundException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(expectedUser));

        Exception exception = assertThrows(UserDetailsFoundException.class, () -> {
            userService.save(expectedUser);
        });
        assertThat(exception.getMessage()).contains("Person with username", "already exists");
    }

    @Test
    public void save_with_existing_email_returns_UserDetailsFoundException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(expectedUser));

        Exception exception = assertThrows(UserDetailsFoundException.class, () -> {
            userService.save(expectedUser);
        });
        assertThat(exception.getMessage()).contains("Person with email", "already exists");
    }

    @Test
    public void updateUserPassword_returns_user_if_he_was_found_by_id() {
        when(userRepository.findById(any())).thenReturn(Optional.of(expectedUser));
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.updateUserPassword(expectedUser.getUserId(), "newPassword");
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void updateUserPassword_if_user_not_found_returns_UserNotFoundException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUserPassword(expectedUser.getUserId(), "gg");
        });
        assertThat(exception.getMessage()).contains("Given User was not found");
    }

    @Test
    public void updateUserPrivacy_returns_user_if_his_details_were_not_found_and_isPrivate_differs_from_current() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(expectedUser));
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        userService.updateUserPrivacy(expectedUser.getEmail(), true);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void findUserByUsername_with_given_correct_username_OK() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(expectedUser));
        userService.findUserByUsername("testUsername");
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void findUserByEmail_with_given_correct_email_OK() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(expectedUser));
        userService.findUserByEmail("testEmail");
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void findUserById_with_given_correct_id_OK() throws Exception {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(expectedUser));
        userService.findUserById(2);
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    public void update_returns_updated_user_OK() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(expectedUser));
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        userService.update(expectedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void update_given_not_existing_user_returns_UserNotFoundException() {
        User newUser = new User(100, "extUsername", "extPassword", "extEmail@mail.com", true, "x", false, null, null, null);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.update(newUser);
        });

        String expectedMessage = "Given User was not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findAllActiveUsers_returns_users() {
        User userExtra1 = new User(null, "username1", "password1", "email1@mail.com", true, "1", true, Date.valueOf("2015-01-01"), null, null);
        User userExtra2 = new User(null, "username2", "password2", "email2@mail.com", true, "2", true, Date.valueOf("2015-01-01"), null, null);
        List<User> testUsers = new ArrayList<>();
        testUsers.add(userExtra1);
        testUsers.add(userExtra2);

        when(userRepository.findAllByIsBlocked(false)).thenReturn(testUsers);
        List<User> actualUsers = userService.findAllActiveUsers();
        assertThat(actualUsers).isEqualTo(testUsers);
    }
}
