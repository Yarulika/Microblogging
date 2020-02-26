package com.sda.microblogging.service;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.PostLike;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.PostLikeRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostLikeServiceTest {

    @Mock
    PostLikeRepository postLikeRepository;
    @InjectMocks
    PostLikeService postLikeService;
    static PostLike postLike;

    @BeforeClass
    public static void beforeClass() throws Exception {
        postLike = new PostLike();
        Post post = new Post();
        post.setId(1);
        User user = new User();
        user.setUserId(1);
        postLike.setPost(post);
        postLike.setUser(user);
    }

    @Test
    public void user_like_post() {
        when(postLikeRepository.save(postLike)).thenReturn(postLike);
        postLikeService.togglePostLike(postLike);
        verify(postLikeRepository).save(postLike);
    }

    @Test
    public void user_can_dislike_post(){

        when(postLikeRepository.findByPostAndUser(postLike.getPost(),postLike.getUser())).thenReturn(java.util.Optional.of(postLike));
        postLikeService.togglePostLike(postLike);
        verify(postLikeRepository).delete(postLike);
    }

    @Test
    public void get_number_of_post_likes_return_number(){

        when(postLikeRepository.countByPost(postLike.getPost())).thenReturn(anyInt());

        assertThat(postLikeService.getNumberOfLikesByPost(postLike.getPost()).intValue());
    }

    @Test
    public void get_list_of_likes_with_user_post_return_list(){
        List listPost = new ArrayList();
        listPost.add(postLike);
        when(postLikeRepository.findByPost(postLike.getPost())).thenReturn(listPost);

        assertThat(postLikeService.findPostLikesAndUserByPost(postLike.getPost())).isEqualTo(listPost);
    }
}
