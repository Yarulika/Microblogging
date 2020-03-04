package com.sda.microblogging.repository;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    public User expectedUser;
    public User actualUser;

    @BeforeEach
    public void initTestData() {
        expectedUser = new User();
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
        actualUser = userRepository.save(expectedUser);
    }

    @AfterEach
    public void clean(){
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void save_returns_saved_user_with_id() {
        assertThat(actualUser.getUserId()).isNotNull();
        assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(actualUser.isPrivate()).isEqualTo(expectedUser.isPrivate());
        assertThat(actualUser.getAvatar()).isEqualTo(expectedUser.getAvatar());
        assertThat(actualUser.isBlocked()).isEqualTo(expectedUser.isBlocked());
        assertThat(actualUser.getCreationDate()).isEqualTo(expectedUser.getCreationDate());
    }

    @Test
    @Order(2)
    public void findByUsername_returns_correct_user_with_requested_username() {
        User actualUser = userRepository.findByUsername(expectedUser.getUsername()).get();
        assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
    }

    @Test
    @Order(3)
    public void findByEmail_returns_correct_user_with_requested_email() {
        User actualUser = userRepository.findByEmail(expectedUser.getEmail()).get();
        assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
    }

    @Test
    @Order(4)
    public void findAllNotBlocked_returns_active_users(){
        User userExtra1 = new User(null, "username1", "password1", "email1@mail.com", true, "1", true, Date.valueOf("2015-01-01"), null, null);
        User userExtra2 = new User(null, "username2", "password2", "email2@mail.com", true, "2", true, Date.valueOf("2015-01-01"), null, null);
        userRepository.save(userExtra1);
        userRepository.save(userExtra2);

        List<User> activeUsers = userRepository.findAllByIsBlocked(false);
        assertThat(activeUsers.parallelStream().allMatch(User::isBlocked)).isFalse();
    }

    @Test
    @Order(5)
    public void delete_deletes_user_correctly() {
        userRepository.delete(expectedUser);
        assertThat(userRepository.findByUsername(expectedUser.getUsername())).isEmpty();
    }
}
