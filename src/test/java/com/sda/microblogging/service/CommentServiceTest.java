package com.sda.microblogging.service;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.ParentCommentNotFoundException;
import com.sda.microblogging.exception.PostNotFoundException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

    @Mock
    UserService userService;

    @InjectMocks
    CommentService commentService;

    private User owningUser;
    private Post post;
    private Comment comment;
    private Comment childComment1;
    private Comment childComment2;
    private List<Comment> comments;
    private List<Comment> subComments;

    @BeforeEach
    public void init(){
        owningUser = new User(33, "ownerUsername", "ownerPassword", "owner@mail.com", true, "?", false, null, null, null);
        post = new Post(77, "post content", false, owningUser, Date.valueOf("2020-01-01"), null,null,null,null);
        comment = new Comment();
        comment.setId(22);
        comment.setContent("comment content");
        comment.setPost(post);
        comment.setOwner(owningUser);
        comment.setCreationDate(Date.valueOf("2020-02-02"));
        comment.setCommentParent(null);

        childComment1 = new Comment(1, "child comment content1", comment.getPost(), comment.getOwner(), Date.valueOf("2020-01-01"), comment);
        childComment2 = new Comment(2, "child comment content2", comment.getPost(), comment.getOwner(), Date.valueOf("2020-02-02"), comment);
        comments = new ArrayList<>();
        comments.add(comment);
        comments.add(childComment1);
        comments.add(childComment2);
        subComments = new ArrayList<>();
        subComments.add(childComment1);
        subComments.add(childComment2);
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
    public void save_with_set_but_not_existing_parent_comment_throws_ParentCommentNotFoundException(){
        when(postService.findPostById(anyInt())).thenReturn(Optional.of(post));
        when(commentRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ParentCommentNotFoundException.class, () -> {
            commentService.save(childComment1);
        });
        assertTrue(exception.getMessage().contains("Provided parent comment was not found"));
    }

    @Test
    public void get_all_comments_for_post(){
        when(postService.findPostById(anyInt())).thenReturn(Optional.of(post));
        when(commentRepository.findCommentsByPostId(post.getId())).thenReturn(comments);
        commentService.findCommentsByPostId(post.getId());
        verify(commentRepository,times(1)).findCommentsByPostId(post.getId());
    }

    @Test
    public void get_all_comments_for_absent_post_throws_PostNotFoundException(){
        when(postService.findPostById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(PostNotFoundException.class, () -> {
            commentService.findCommentsByPostId(post.getId());
        });
        assertThat(exception.getMessage()).isEqualTo("Provided Post was not found");
    }

    @Test
    public void get_all_sub_comments_for_parent_comment(){
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));
        when(commentRepository.findCommentsByCommentParentId(comment.getId())).thenReturn(subComments);
        commentService.findCommentsByCommentParentId(comment.getId());
        verify(commentRepository, times(1)).findCommentsByCommentParentId(comment.getId());
    }

    @Test
    public void get_all_sub_comments_for_absent_parent_comment_throws_ParentCommentNotFoundException(){
        when(commentRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ParentCommentNotFoundException.class, () -> {
            commentService.findCommentsByCommentParentId(comment.getId());
        });
        assertThat(exception.getMessage()).isEqualTo("Provided parent comment was not found");
    }

    @Test
    public void findNumberOfRepliedComments_returns_number(){
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));
        when(commentRepository.countByCommentParentId(comment.getId())).thenReturn(subComments.size());
        commentService.findNumberOfRepliedComments(comment.getId());
        verify(commentRepository, times(1)).countByCommentParentId(comment.getId());
    }
}
