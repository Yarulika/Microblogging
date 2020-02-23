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
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User actualUser = userService.save(expectedUser);

        assertEquals(expectedUser, actualUser);
        assertEquals(actualUser.getUsername(), expectedUser.getUsername());
    }

    @Test
    public void save_with_existing_username_returns_UserDetailsFoundException(){
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(expectedUser));

        Exception exception = assertThrows(UserDetailsFoundException.class, () -> {
            userService.save(expectedUser);
        });
        assertThat(exception.getMessage()).contains("Person with username", "already exists");
    }

    @Test
    public void save_with_existing_email_returns_UserDetailsFoundException(){
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(expectedUser));

        Exception exception = assertThrows(UserDetailsFoundException.class, () -> {
            userService.save(expectedUser);
        });
        assertThat(exception.getMessage()).contains("Person with email", "already exists");
    }

    @Test
    public void findUserByUsername_with_given_correct_username_OK() {
        String testUsername = expectedUser.getUsername();
        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findUserByUsername(testUsername).get();
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test
    public void findUserByEmail_with_given_correct_email_OK(){
        String testEmail = expectedUser.getEmail();
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findUserByEmail(testEmail).get();
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test
    public void findUserById_with_given_correct_id_OK() throws Exception{
        int testId = expectedUser.getUserId();
        when(userRepository.findById(testId)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findUserById(testId).orElseThrow(Exception::new);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void update_returns_updated_user_OK(){
        int savedId = expectedUser.getUserId();
        // Test Data for updatedUser
        Role role = new Role(2, RoleTitle.USER);
        User userExtra1 = new User(10, "extUsername", "extPassword", "extEmail@mail.com", true, "x", false, null, null, null, null);
        User userExtra2 = new User(20, "xtUsername", "xtPassword", "xtEmail@mail.com", false, "xx", false, null, null, null, null);
        HashSet<User> blockedUsersSet = new HashSet<>();
        blockedUsersSet.add(userExtra1);
        blockedUsersSet.add(userExtra2);
        // TODO add set of Followers

        User updatedUser = new User();
        updatedUser.setUserId(savedId);
        updatedUser.setUsername("updName");
        updatedUser.setPassword("updPassword");
        updatedUser.setEmail("updName@mail.com");
        updatedUser.setPrivate(true);
        updatedUser.setAvatar("updAvatar");
        updatedUser.setBlocked(true);
        updatedUser.setCreationDate(Date.valueOf("2010-01-01"));
        updatedUser.setRole(role);
        updatedUser.setBlockedUsers(blockedUsersSet);
        updatedUser.setFollowers(null);

        when(userRepository.findById(savedId)).thenReturn(Optional.of(expectedUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        User actualUser = userService.update(updatedUser);
        assertEquals(updatedUser, actualUser);
    }

    @Test
    public void update_given_not_existing_user_returns_UserNotFoundException(){
        User newUser = new User(100, "extUsername", "extPassword", "extEmail@mail.com", true, "x", false, null, null, null, null);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.update(newUser);
        });

        String expectedMessage = "Given User was not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
