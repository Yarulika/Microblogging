package com.sda.microblogging.controller;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.AvatarService;
import com.sda.microblogging.service.FollowerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AvatarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private FollowerService followerService;

    @Test
    public void saveAvatar_returns_CREATED() throws Exception {
        User user = new User(22, "username", "password", "email@mail.com", true, "cool I", false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        byte[] avatarFileBytes = "some img".getBytes();
        MockMultipartFile avatarFile = new MockMultipartFile("avatar", "filename.txt", "text/plain", avatarFileBytes);

        when(avatarService.saveAvatar(anyInt(), isA(byte[].class))).thenReturn("localhost:8080/microblogging/v1/user/avatar/?avatarPath=/tmp/avatars/anna.jpeg");

        ResultActions result = mockMvc
                .perform(multipart("/microblogging/v1/user/uploadAvatar")
                        .file(avatarFile)
                .param("userId", "22"))
                .andDo(print());

        result
                .andExpect(status().isCreated())
                .andReturn();
    }
}
