package com.sda.microblogging.controller;

import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUserByValidUsername_returnsUser_OK() throws Exception {
        //given
        String username = "usernameX";
        User user = new User(null, username, "password", "email", true, null, false, null, null, null, null);
        when(userService.findUserByUsername(username)).thenReturn(Optional.of(user));

        // when
        ResultActions result = mockMvc
                .perform(
                        get("/users/usernameX")
                ).andDo(print());
        //then
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("username").value(username))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andReturn();
    }
}
