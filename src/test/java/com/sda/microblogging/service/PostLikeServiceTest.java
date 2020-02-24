package com.sda.microblogging.service;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.PostLike;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.PostLikeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostLikeServiceTest {

    @Mock
    PostLikeRepository postLikeRepository;
    @InjectMocks
    PostLikeService postLikeService;

    @Test
    public void user_like_post() {
        Post post = new Post();
        post.setId(1);
        User user = new User();
        user.setUserId(1);
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setUser(user);

        when(postLikeRepository.save(postLike)).thenReturn(postLike);

        PostLike actualPostLike = postLikeService.save(postLike);

        assertThat(actualPostLike).isEqualTo(postLike);
    }

    @Test
    public void user_can_dislike_post(){
        Post post = new Post();
        post.setId(1);
        User user = new User();
        user.setUserId(1);
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setUser(user);
        postLikeService.dislikePost(postLike);
        verify(postLikeRepository).delete(postLike);
    }
}
