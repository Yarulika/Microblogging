package com.sda.microblogging.repository;

import com.sda.microblogging.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername() {
        String testUsername = "username";

        User expectedUser = new User(null, testUsername, "password", "email", true, "avatar", false, Date.valueOf("2020-01-01"), null, null, null);
        userRepository.save(expectedUser);

        User actualUser = userRepository.findByUsername(testUsername);
        assertEquals(expectedUser, actualUser);
    }
}