package com.sda.microblogging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.*;
import com.sda.microblogging.entity.DTO.comment.CommentNewInputDTO;
import com.sda.microblogging.service.CommentLikeService;
import com.sda.microblogging.service.CommentService;
import com.sda.microblogging.service.PostService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @MockBean
    private CommentLikeService commentLikeService;

    public User user;
    public Post post;
    public Comment comment1;
    public Comment comment2;
    public Comment comment3;
    public Comment comment4;
    public Comment[] comments;
    public CommentLike commentLike;
    public CommentNewInputDTO commentNewInputDTO;

    @BeforeEach
    public void initTestData(){
        user = new User(1, "username0", "password0", "email0@mail.com", true, "avatar", false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        post = new Post(1, "post content", false, user, Date.valueOf("2020-02-02"), null, null,null,null);
        comment1 = new Comment(1, "comments content1", post, user, Date.valueOf("2020-02-02"), null);
        comment2 = new Comment(2, "comments content2", post, user, Date.valueOf("2020-02-02"), null);
        comment3 = new Comment(3, "comments content3", post, user, Date.valueOf("2020-02-02"), comment2);
        comment4 = new Comment(4, "comments content4", post, user, Date.valueOf("2020-02-02"), comment2);
        comments = new Comment[4];
        comments[0] = comment1;
        comments[1] = comment2;
        comments[2] = comment3;
        comments[3] = comment4;
        commentLike = new CommentLike(1, comment1, user, Date.valueOf("2020-02-02"));
        commentNewInputDTO = new CommentNewInputDTO("comments content1", post.getId(), user.getUserId(), comment1.getId());
    }

    @Test
    public void saveNew_CommentNewInputDTO_returns_CommentSavedDTO_with_Created_status() throws Exception {
        when(commentService.findCommentById(anyInt())).thenReturn(Optional.of(comment1));
        when(postService.findPostById(anyInt())).thenReturn(Optional.of(post));
        when(userService.findUserById(anyInt())).thenReturn(Optional.of(user));
        when(commentService.save(any(Comment.class))).thenReturn(comment1);

        ResultActions result = mockMvc
                .perform(
                        post("/microblogging/v1/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentNewInputDTO))
                )
                .andDo(print());

        result
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andReturn();
    }

    @Test
    public void toggleCommentLike_returns_status_Created() throws Exception{
        ResultActions result = mockMvc
                .perform(
                        post("/microblogging/v1/comment/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentLike))
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
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andReturn();
    }

    //TODO @Test if findCommentsByPostId_returns_PostId_not_found

    @Test
    public void findAllSubComments_for_comment_returns_collection_status_isFound() throws Exception{
        when(commentService.findCommentsByCommentParentId(anyInt())).thenReturn(Arrays.asList(comments));
        ResultActions results = mockMvc
                .perform(
                    get("/microblogging/v1/comment/2"))
                .andDo(print());
        results
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*",hasSize(4)))
                .andReturn();
    }

    //TODO @Test if findAllSubComments_returns_ParentComment_not_found
}
