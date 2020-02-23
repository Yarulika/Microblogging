package com.sda.microblogging.repository;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void save_returns_saved_user(){
        User expectedUser = new User();
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

        User actualUser =  userRepository.save(expectedUser);

        assertThat(actualUser.getUserId()).isNotNull();
        actualUser.setUserId(null);
        expectedUser.setUserId(null);
        assertThat(actualUser).isEqualTo(expectedUser);
    }

}