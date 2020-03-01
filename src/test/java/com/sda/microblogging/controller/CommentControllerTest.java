package com.sda.microblogging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.service.CommentService;
import com.sda.microblogging.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Array;
import java.sql.Date;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PostService postService;

    @Test
    public void saveNew_returns_saved_comment() throws Exception{
        User user0 = new User(1, "username0", "password0", "email0@mail.com", true, null, false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        Post post = new Post(1, "post content", false, user0, Date.valueOf("2020-02-02"), null, null);
        Comment comment = new Comment(1, "comments content", post, user0, Date.valueOf("2020-02-02"), null);

        when(commentService.save(comment)).thenReturn(comment);
        ResultActions result = mockMvc
                .perform(
                        post("/microblogging/v1/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(comment))
                )
                .andDo(print());

        result
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void findAllComments_for_post_returns_collection() throws Exception{
        User user0 = new User(1, "username0", "password0", "email0@mail.com", true, null, false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        Post post = new Post(1, "post content", false, user0, Date.valueOf("2020-02-02"), null, null);
        Comment comment1 = new Comment(1, "comments content1", post, user0, Date.valueOf("2020-02-02"), null);
        Comment comment2 = new Comment(2, "comments content2", post, user0, Date.valueOf("2020-02-02"), null);
        Comment comment3 = new Comment(3, "comments content3", post, user0, Date.valueOf("2020-02-02"), comment2);
        Comment[] comments = new Comment[3];

        //    GET micr../v1/post/{postId}/comment/

        when(commentService.findCommentsByPost(post.getId())).thenReturn(Arrays.asList(comments));
//        ResultActions result = mockMvc
//                .perform(
//                        get("/microblogging/v1/post")
//                )



    }

    @Test
    public void findAllSubComments_for_comment_returns_collection() throws Exception{
        User user0 = new User(1, "username0", "password0", "email0@mail.com", true, null, false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        Post post = new Post(1, "post content", false, user0, Date.valueOf("2020-02-02"), null, null);
        Comment comment1 = new Comment(1, "comments content1", post, user0, Date.valueOf("2020-02-02"), null);
        Comment comment2 = new Comment(2, "comments content2", post, user0, Date.valueOf("2020-02-02"), comment1);
        Comment comment3 = new Comment(3, "comments content3", post, user0, Date.valueOf("2020-02-02"), comment1);

//TODO

    }

}
