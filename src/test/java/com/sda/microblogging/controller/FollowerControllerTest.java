package com.sda.microblogging.controller;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.FollowerService;
import com.sda.microblogging.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class FollowerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private FollowerService followerService;

    public User user0;
    public User user1;
    public User user2;
    public Follower[] followers;

    @BeforeEach
    public void initTestData() {
        user0 = new User(1, "username0", "password0", "email0@mail.com", true, null, false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        user1 = new User(2, "username1", "password1", "email1mail.com", true, null, false, Date.valueOf("2020-02-02"), new Role(2, RoleTitle.USER), null);
        user2 = new User(3, "username2", "password2", "email2mail.com", true, null, false, Date.valueOf("2020-02-02"), new Role(2, RoleTitle.USER), null);
        Follower follower0 = new Follower(1, user0, user1, Date.valueOf("2020-01-01"));
        Follower follower1 = new Follower(2, user0, user2, Date.valueOf("2020-01-01"));
        followers = new Follower[2];
        followers[0] = follower0;
        followers[1] = follower1;
    }

    @Test
    public void findAllFollowersByUsername_returns_collection_OK_status() throws Exception {
        when(userService.findUserByUsername(anyString())).thenReturn(Optional.of(user0));
        when(followerService.getAllFollowersByUserId(anyInt())).thenReturn(Arrays.asList(followers));

        ResultActions result = mockMvc
                .perform(
                        get("/microblogging/v1/follower/username0/followers"))
                .andDo(print());
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();
    }

    @Test
    public void findAllFollowingByFollowerUsername_returns_collection_OK_status() throws Exception {
        when(followerService.getAllFollowingByFollowerUsername(anyString())).thenReturn(Arrays.asList(followers));

        ResultActions result = mockMvc
                .perform(
                        get("/microblogging/v1/follower/username2/followings"))
                .andDo(print());
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();
    }
}
