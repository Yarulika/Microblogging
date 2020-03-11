package com.sda.microblogging.controller;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.DTO.post.PostSaveDTO;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.PostLike;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.entity.mapper.PostMapper;
import com.sda.microblogging.service.PostLikeService;
import com.sda.microblogging.service.PostService;
import lombok.SneakyThrows;
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

import static com.sda.microblogging.controller.UserControllerTest.asJsonString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private PostLikeService postLikeService;

    @Test
    public void findAllPublicPosts_returns_collection_and_Found() throws Exception {
        User user = new User(1, "username0", "password0", "email0@mail.com", true, "avatar", false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        Post post = new Post(1, "post content", false, user, Date.valueOf("2020-02-02"), null, null,null,null);
        Post[] posts = new Post[1];
        posts[0] = post;

        when(postService.findAllPostsBasedOnPrivacy(false)).thenReturn(Arrays.asList(posts));

        ResultActions result = mockMvc
                .perform(
                        get("/microblogging/v1/post/allPublic"))
                .andDo(print());
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].*", hasSize(13)))
                .andReturn();
    }

    @SneakyThrows
    @Test
    public void save_post_returns_CREATED() {
        User user = new User(1, "username0", "password0", "email0@mail.com", true, "avatar", false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        Post post = new Post(null, "post content", false, user, Date.valueOf("2020-02-02"), null, null,null,null);
        Post savedPost = new Post(1, "post content", false, user, Date.valueOf("2020-02-02"), null, null,null,null);

        when(postService.save(any(Post.class))).thenReturn(savedPost);

        ResultActions result     = mockMvc
                .perform(
                        post("/microblogging/v1/post/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(post))
                                )
                .andDo(print());

        result
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("postId").value("1"))
                .andExpect(jsonPath("content").value("post content"))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(13)))
                .andReturn();
    }

    @Test
    public void get_posts_with_not_actual_username_return_not_found() throws Exception {

        ResultActions resultActions = mockMvc
                .perform(
                        get("/microblogging/v1/post/byUsername/"))
                .andDo(print());
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    public void likePost_returns_ok() throws Exception{
        PostLike postLike=  new PostLike();

        Post p = new Post();
        p.setId(1);

        User u = new User();
        u.setUserId(1);

        postLike.setUser(u);
        postLike.setPost(p);

        ResultActions resultActions =
                mockMvc.perform(
                        post("/microblogging/v1/post/like")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(postLike)))
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }
    @Test
    public void sharePost_returns_CREATED() throws Exception{
        User user = new User(1, "username0", "password0", "email0@mail.com", true, "avatar", false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        Post post = new Post(1, "post content", false, user, Date.valueOf("2020-02-02"), null, null,null,null);

        when(postService.save(any(Post.class))).thenReturn(post);

        mockMvc.perform(
                post("/microblogging/v1/post/share")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(post)))
                .andExpect(status().isCreated())
                .andReturn();

        verify(postService,times(1)).save(any(Post.class));
    }

    @Test
    public void findAllMyFollowingsAndPublicPosts_by_incorrect_userId_returns_ok() throws Exception{

        mockMvc.perform(
                get("/microblogging/v1/post/byUserId/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}