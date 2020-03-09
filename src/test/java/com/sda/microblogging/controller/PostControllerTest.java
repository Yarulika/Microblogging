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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static com.sda.microblogging.controller.UserControllerTest.asJsonString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        // TODO // this test is DONE: I added @ResponseStatus(HttpStatus.FOUND) to method findAllPublicPosts() to return 302 (was it desired?)
        // TODO // I want postService.findAllPostsBasedOnPrivacy return posts and check size of first element: so it really went through the mapper (size = 13)
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
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].*", hasSize(13)))
                .andReturn();
    }

    @SneakyThrows
    @Test
    public void save_post_returns_CREATED() {
        // TODO // DONE, was missing action:  when(postService.save(any(Post.class))).thenReturn(post);
        User user = new User(1, "username0", "password0", "email0@mail.com", true, "avatar", false, Date.valueOf("2020-01-01"), new Role(2, RoleTitle.USER), null);
        Post post = new Post(1, "post content", false, user, Date.valueOf("2020-02-02"), null, null,null,null);

        when(postService.save(any(Post.class))).thenReturn(post);

        ResultActions result = mockMvc
                .perform(
                        post("/microblogging/v1/post/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(user))
                                )
                .andDo(print());

        result
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andReturn();
    }

    @Test
    public void get_posts_with_username() throws Exception {
        // TODO // I DID NOT CHECK IT! DO IT

        ResultActions resultActions = mockMvc.perform(get("/microblogging/v1/post/byUsername/NotExists"))
                .andExpect(status().isNotFound());
        resultActions
                .andExpect(jsonPath("$.*",hasSize(0)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void post_like() throws Exception{
        // TODO // I DID NOT CHECK IT! DO IT

        PostLike postLike=  new PostLike();
        Post p = new Post();
        p.setId(1);
        User u = new User();
        u.setUserId(1);
        postLike.setUser(u);
        postLike.setPost(p);


        RequestBuilder requestBuilder;
        ResultActions resultActions = mockMvc.perform(post("/microblogging/v1/post/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postLike)))
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }
    @Test
    public void post_share_return_bad_request() throws Exception{
        // TODO // I DID NOT CHECK IT! DO IT

        RequestBuilder requestBuilder;
        ResultActions resultActions = mockMvc.perform(post("/microblogging/v1/post/share")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(null)))
                .andDo(print());

        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllMyFollowingsAndPublicPosts_by_incorrect_userId_returns_isNotFound() throws Exception{
        // TODO // this functionality: throwing isNotFound exception even does not implemented!
        // TODO // I DID NOT CHECK IT! DO IT

        PostMapper postMapper = new PostMapper();
        List<Post> postDTOS = Arrays.asList(postMapper.convertDtoToPost(new PostSaveDTO()));

        when(postService.findAllPostsAndMyFollowingsPost(1)).thenReturn(postDTOS);

        ResultActions resultActions = mockMvc.perform(get("/microblogging/v1/post/byUserId/userId")
                .param("userId","0")
                // TODO Remove .param as it is web request parameter, like: /books?category=java so .param("category", "java")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(status().isNotFound());

        resultActions
                .andExpect(status().isNotFound())
        .andReturn();
    }
}