package com.sda.microblogging.service;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.PostRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @Mock
    PostRepository postRepository;
    @InjectMocks
    PostService postService;
    private static List<Post> posts;

    @BeforeClass
    public static void beforeClass() throws Exception {
        User user = new User();
        user.setUsername("muhamet");
        user.setUserId(1);

        posts = new ArrayList<>();
        Post post = new Post();
        post.setId(1);
        post.setContent("test");
        post.setCreationDate(Date.valueOf("2020-01-01"));
        post.setOwner(user);

        Post post1 = new Post();
        post1.setId(1);
        post1.setContent("test22");
        post1.setCreationDate(Date.valueOf("2020-01-01"));
        post1.setOwner(user);

        posts.add(post);
        posts.add(post1);
    }

    @Test
    public void get_all_posts(){

        when(postRepository.findAll()).thenReturn(posts);
        List<Post> actualPosts = postService.findAll();

        assertEquals(2,actualPosts.size());
        verify(postRepository,times(1)).findAll();
    }
    @Test
    public void get_all_posts_sorted_by_date(){

        when(postRepository.findByOrderByCreationDate()).thenReturn(posts);
        List<Post> actualPosts = postService.findByOrderByCreationDate();

        assertEquals(2,actualPosts.size());
        verify(postRepository,times(1)).findByOrderByCreationDate();
    }

    @Test
    public void get_posts_by_userId() {
        when(postRepository.findPostsByOwner(1)).thenReturn(posts);

        List<Post> postList = postService.findPostsByOwner(1);

        assertEquals("muhamet",postList.get(1).getOwner().getUsername());
    }

    @Test(expected = NullPointerException.class)
    public void post_without_content_check() {
        Post post = new Post();
        post.setId(1);

        postService.save(post);
    }

    @Test
    public void getSharedPosts_return_number_of_sharded_post() {

        when(postRepository.findNumberOfSharesOfPost(Mockito.anyInt())).thenReturn(1);

        int numberOfSharedPosts = postService.findNumberOfSharesOfPost(1);

        assertEquals(1,numberOfSharedPosts);
        verify(postRepository,times(1)).findNumberOfSharesOfPost(1);
    }
}