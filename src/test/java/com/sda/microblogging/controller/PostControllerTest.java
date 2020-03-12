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
import com.sda.microblogging.service.UserService;
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

    @Autowired
    PostMapper postMapper;

    @MockBean
    UserService userService;

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
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].*", hasSize(13)))
                .andReturn();
    }

    @SneakyThrows
    @Test
    public void save_post_returns_bad_request() {
        Post post = new Post(1, "post content", false, new User(), Date.valueOf("2020-02-02"), null, null,null,null);

        when(postService.save(any(Post.class))).thenReturn(post);

        ResultActions result     = mockMvc
                .perform(
                        post("/microblogging/v1/post/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(post))
                                )
                .andDo(print());

        result
                .andExpect(status().isBadRequest())
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
    public void post_like_return_ok() throws Exception{
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
    public void post_share_return_bad_request() throws Exception{

        Post post = new Post();
        PostSaveDTO postSaveDTO = new PostSaveDTO();
        postSaveDTO.setContent("asa");
        postSaveDTO.setCreationDate(Date.valueOf("2020-01-04"));
        postSaveDTO.setOwner(1);
        postSaveDTO.setIsEdited(false);
        User user= new User();
        user.setUserId(1);

        when(userService.findUserById(1)).thenReturn(java.util.Optional.of(user));
        post = postMapper.convertPostSaveDTOtoPost(postSaveDTO);


        mockMvc.perform(
                post("/microblogging/v1/post/share")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(post)))
                .andDo(print())
                .andExpect(status().isBadRequest());
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