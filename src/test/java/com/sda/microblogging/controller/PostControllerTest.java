package com.sda.microblogging.controller;


import com.sda.microblogging.entity.DTO.post.PostDTO;
import com.sda.microblogging.entity.DTO.post.PostSaveDTO;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.PostLike;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.entity.mapper.PostMapper;
import com.sda.microblogging.service.PostLikeService;
import com.sda.microblogging.service.PostService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static com.sda.microblogging.controller.UserControllerTest.asJsonString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostMapper postMapper;
    @MockBean
    private PostService postService;
    @MockBean
    private PostLikeService postLikeService;

    @Test
    public void get_public_posts() throws Exception {

        Post post = new Post();
        post.setId(1);

        ResultActions result = mockMvc
                .perform(
                        get("/home/public/post"))
                .andDo(print());
        result
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();
    }

    @SneakyThrows
    @Test
    public void save_post_return_ok() {
        PostSaveDTO postSaveDTO = new PostSaveDTO();
        postSaveDTO.setContent("asa");
        postSaveDTO.setCreationDate(Date.valueOf("2020-01-01"));
        User user = new User();
        user.setUserId(1);
        postSaveDTO.setOwner(user);

        ResultActions result = mockMvc
                .perform(
                        post("/home/post/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        result
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void get_posts_with_username() throws Exception {


        ResultActions resultActions = mockMvc.perform(get("/home/posts/NotExists"))
                .andExpect(status().isNotFound());
        resultActions
                .andExpect(jsonPath("$.*",hasSize(0)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void post_like() throws Exception{
        PostLike postLike=  new PostLike();
        Post p = new Post();
        p.setId(1);
        User u = new User();
        u.setUserId(1);
        postLike.setUser(u);
        postLike.setPost(p);


        RequestBuilder requestBuilder;
        ResultActions resultActions = mockMvc.perform(post("/home/post/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postLike)))
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }
    @Test
    public void post_share_return_bad_request() throws Exception{


        RequestBuilder requestBuilder;
        ResultActions resultActions = mockMvc.perform(post("/home/post/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(null)))
                .andDo(print());

        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    public void find_posts_by_users() throws Exception{


        List<Post> postDTOS = Arrays.asList(postMapper.convertDtoToPost(new PostSaveDTO()));

        when(postService.findAllPostsAndMyFollowingsPost(1)).thenReturn(postDTOS);

        ResultActions resultActions = mockMvc.perform(get("/home/userId")
                .param("userId","0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(status().isNotFound());

        resultActions
                .andExpect(status().isNotFound())
        .andReturn();
    }
}