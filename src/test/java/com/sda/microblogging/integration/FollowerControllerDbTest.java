package com.sda.microblogging.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.DTO.follower.FollowDTO;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.FollowerRepository;
import com.sda.microblogging.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class FollowerControllerDbTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowerRepository followerRepository;

    public User user0, expectedUser0, user1, expectedUser1, user2, expectedUser2;
    public Follower[] followers;
    public Follower follower0, follower1;

    @BeforeEach
    public void initTestData() {
        expectedUser0 = new User(null, "username0", "password0", "email0@mail.com", true, "avatar", false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        user0 = userRepository.save(expectedUser0);
        expectedUser1 = new User(null, "username1", "password1", "email1mail.com", true, "avatar", false, Date.valueOf("2020-02-02"), new Role(2, RoleTitle.USER), null);
        user1 = userRepository.save(expectedUser1);
        expectedUser2 = new User(null, "username2", "password2", "email2mail.com", true, "avatar", false, Date.valueOf("2020-02-02"), new Role(2, RoleTitle.USER), null);
        user2 = userRepository.save(expectedUser2);

        follower0 = new Follower(1, user0, user1, Date.valueOf("2020-01-01"));
        follower1 = new Follower(2, user0, user2, Date.valueOf("2020-01-01"));
        followers = new Follower[2];
        followers[0] = follower0;
        followers[1] = follower1;
    }

    @AfterEach
    public void clean(){
        followerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void followUser_returns_FollowerDTO_status_OK() throws Exception {
        FollowDTO followDTO = new FollowDTO(user0.getUserId(), user1.getUserId());

        ResultActions result = mockMvc
                .perform(
                        post("/microblogging/v1/follower/follow")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(followDTO))
                )
                .andDo(print());
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("username").value("username0"))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andReturn();
    }
}
