package com.sda.microblogging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.CommentService;
import com.sda.microblogging.service.PostService;
import com.sda.microblogging.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Array;
import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    public User user;
    public Post post;
    public Comment comment1;
    public Comment comment2;
    public Comment comment3;
    public Comment comment4;
    private Comment[] comments;

    @BeforeEach
    public void initTestData(){
        user = new User(1, "username0", "password0", "email0@mail.com", true, null, false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        post = new Post(1, "post content", false, user, Date.valueOf("2020-02-02"), null, null);
        comment1 = new Comment(1, "comments content1", post, user, Date.valueOf("2020-02-02"), null);
        comment2 = new Comment(2, "comments content2", post, user, Date.valueOf("2020-02-02"), null);
        comment3 = new Comment(3, "comments content3", post, user, Date.valueOf("2020-02-02"), comment2);
        comment4 = new Comment(4, "comments content4", post, user, Date.valueOf("2020-02-02"), comment2);
        comments = new Comment[4];
        comments[0] = comment1;
        comments[1] = comment2;
        comments[2] = comment3;
        comments[3] = comment4;
    }

    @Test
    public void saveNew_returns_saved_comment() throws Exception{
        when(commentService.save(comment1)).thenReturn(comment1);
        ResultActions result = mockMvc
                .perform(
                        post("/microblogging/v1/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(comment1))
                )
                .andDo(print());

        result
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void findCommentsByPostId_returns_collection_status_isFound() throws Exception{
        when(commentService.findCommentsByPostId(anyInt())).thenReturn(Arrays.asList(comments));
        ResultActions result = mockMvc
                .perform(
                    get("/microblogging/v1/post/1/comment"))
                .andDo(print());
        result
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andReturn();
    }

    @Test
    public void findAllSubComments_for_comment_returns_collection_status_isFound() throws Exception{
        when(commentService.findCommentsByCommentParentId(anyInt())).thenReturn(Arrays.asList(comments));

        ResultActions results = mockMvc
                .perform(
                    get("/microblogging/v1/comment/2"))
                .andDo(print());
        results
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*",hasSize(4)))
                .andReturn();
    }

    // TODO Update to return DTOs
    // TODO add info about likes
}
