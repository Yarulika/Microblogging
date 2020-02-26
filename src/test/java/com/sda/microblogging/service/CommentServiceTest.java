package com.sda.microblogging.service;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.PostNotFoundException;
import com.sda.microblogging.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    PostService postService;

    @InjectMocks
    CommentService commentService;

    private Comment comment;
    private Post post;
    private List<Comment> comments;

    @BeforeEach
    public void init(){
        comment = new Comment();
        User owningUser = new User(33, "ownerUsername", "ownerPassword", "owner@mail.com", true, "?", false, null, null, null, null);
        post = new Post(77, "post content", false, owningUser, Date.valueOf("2020-01-01"), null, null);

        comment.setContent("comment content");
        comment.setPost(post);
        comment.setOwner(owningUser);
        comment.setCreationDate(Date.valueOf("2020-02-02"));
        comment.setCommentParent(null);

        Comment childComment1 = new Comment(null, "child comment content1", comment.getPost(), comment.getOwner(), Date.valueOf("2020-01-01"), comment);
        Comment childComment2 = new Comment(null, "child comment content2", comment.getPost(), comment.getOwner(), Date.valueOf("2020-02-02"), comment);

        comments = new ArrayList<>();
        comments.add(comment);
        comments.add(childComment1);
        comments.add(childComment2);
    }

    @Test
    public void save_returns_saved_comment(){
        when(postService.findPostById(anyInt())).thenReturn(Optional.of(post));
        when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(comment);
        Comment actualComment = commentService.save(comment);
        assertThat(comment).isEqualTo(actualComment);
    }

    @Test void save_with_not_existing_post_throws_PostNotFoundException(){
        when(postService.findPostById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(PostNotFoundException.class, () -> {
            commentService.save(comment);
        });
        assertTrue(exception.getMessage().contains("Provided Post was not found"));
    }

    @Test
    public void get_all_comments_for_post(){
        when(commentRepository.findCommentsByPost(77)).thenReturn(comments);
        List<Comment> actualComments = commentService.findCommentsByPost(77);

        verify(commentRepository,times(1)).findCommentsByPost(77);
    }
}
