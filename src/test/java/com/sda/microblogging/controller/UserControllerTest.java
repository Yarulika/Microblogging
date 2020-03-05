package com.sda.microblogging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.DTO.user.UserSignUpDTO;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.FollowerService;
import com.sda.microblogging.service.UserService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private FollowerService followerService;

    @Test
    public void getAllActiveUsers_returns_collection_of_active_users() throws Exception {
        User user0 = new User(1, "username1", "password1", "email1@mail.com", true, null, false, Date.valueOf("2020-01-01"), new Role(1, RoleTitle.ADMIN), null);
        User user1 = new User(2, "username2", "password2", "email2mail.com", true, null, false, Date.valueOf("2020-02-02"), new Role(1, RoleTitle.ADMIN), null);
        User[] users = new User[2];
        users[0] = user0;
        users[1] = user1;

        when(userService.findAllActiveUsers()).thenReturn(Arrays.asList(users));
        ResultActions result = mockMvc
                .perform(
                        get("/microblogging/v1/user/allActive"))
                .andDo(print());
        result
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();
    }

    @Test
    public void getAllFollowingUsers_returns_collection() throws Exception{
        User user0 = new User(1, "username0", "password0", "email0@mail.com", true, null, false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        User user1 = new User(2, "username1", "password1", "email1mail.com", true, null, false, Date.valueOf("2020-02-02"), new Role(2, RoleTitle.USER), null);
        User user2 = new User(3, "username2", "password2", "email2mail.com", true, null, false, Date.valueOf("2020-02-02"), new Role(2, RoleTitle.USER), null);
        Follower follower0 = new Follower(1, user0, user1, Date.valueOf("2020-01-01"));
        Follower follower1 = new Follower(2, user0, user2, Date.valueOf("2020-01-01"));
        Follower[] followers = new Follower[2];
        followers[0] = follower0;
        followers[1] = follower1;
        String username = user0.getUsername();

        when(userService.findUserByUsername(user0.getUsername())).thenReturn(Optional.of(user0));
        when(followerService.getAllFollowingByFollowerId(user0.getUserId())).thenReturn(Arrays.asList(followers));

        ResultActions result = mockMvc
                .perform(
                        get("/microblogging/v1/user/username0/followings"))
                .andDo(print());
        result
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();
    }

    @Test
    public void getAllActiveUsers_returns_empty_collection_if_none_present() throws Exception {
        User[] users = new User[0];
        when(userService.findAllActiveUsers()).thenReturn(Arrays.asList(users));
        ResultActions result = mockMvc
                .perform(
                        get("/microblogging/v1/user/allActive"))
                .andDo(print());
        result
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andReturn();
    }

    @Test
    public void getUserByValidUsername_returnsUser() throws Exception {
        String username = "usernameX";
        User user = new User(1, username, "password", "email", true, null, false, null, null, null);
        when(userService.findUserByUsername(username)).thenReturn(Optional.of(user));
        ResultActions result = mockMvc
                .perform(
                        get("/microblogging/v1/user/usernameX")
                ).andDo(print());
        result
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("username").value(username))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andReturn();
    }

    @Test
    public void getUserByUsername_if_none_returns_NOT_FOUND() throws Exception {
        ResultActions result = mockMvc
                .perform(
                        get("/microblogging/v1/user/any")
                ).andDo(print());
        result
                .andExpect(status().isNotFound());
    }

    @Test
    public void signUp_new_user_returns_CREATED() throws Exception {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO( "username", "email@mail.com", "password");
        User savedUser = new User(22, "username", "password", "email@mail.com", false, "cool me", false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        when(userService.save(any(User.class))).thenReturn(savedUser);
        ResultActions result = mockMvc
                .perform(
                        post("/microblogging/v1/user/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userSignUpDTO))
                        )
                .andDo(print());

        result
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("userId").value("22"))
                .andExpect(jsonPath("username").value("username"))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andReturn();
    }

    @Test
    public void signUp_new_with_insufficient_details_returns_NotAcceptable_status() throws Exception {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO( "username", null, null);
        ResultActions result = mockMvc
                .perform(
                        post("/microblogging/v1/user/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userSignUpDTO))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        result
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
