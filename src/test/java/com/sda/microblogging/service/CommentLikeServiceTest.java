package com.sda.microblogging.service;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.CommentLike;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.CommentLikeRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentLikeServiceTest {
    @Mock
    CommentLikeRepository commentLikeRepository;
    @InjectMocks
    CommentLikeService commentLikeService;
    static CommentLike commentLike;

    @BeforeClass
    public static void beforeClass() throws Exception {
        commentLike = new CommentLike();
        Comment comment = new Comment();
        comment.setId(1);
        User user = new User();
        user.setUserId(1);
        commentLike.setComment(comment);
        commentLike.setUser(user);
    }

    @Test
    public void user_like_comment_returns_comment_like(){

        when(commentLikeRepository.save(commentLike)).thenReturn(commentLike);
        commentLikeService.toggleCommentLike(commentLike);
        verify(commentLikeRepository).save(commentLike);
    }

    @Test
    public void user_dislike_comment(){
        when(commentLikeRepository.findByCommentAndUser(commentLike.getComment(),commentLike.getUser())).thenReturn(java.util.Optional.ofNullable(commentLike));
        commentLikeService.toggleCommentLike(commentLike);

        verify(commentLikeRepository).delete(commentLike);
    }

    @Test
    public void get_number_of_comment_likes_return_number(){

        when(commentLikeService.getNumberOfCommentLikes(commentLike.getComment().getId())).thenReturn(anyInt());

        assertThat(commentLikeService.getNumberOfCommentLikes(commentLike.getComment().getId())).isEqualTo(0);
    }

}
