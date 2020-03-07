package com.sda.microblogging.repository;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public User owningUser;
    public Post post;
    public Comment comment;
    public Comment childComment1;
    public Comment childComment2;

    @BeforeEach
    public void initTestData() {
        Role role = new Role(1, RoleTitle.ADMIN);
        owningUser = new User(null, "ownerUsername", "ownerPassword", "owner@mail.com", true, "?", false, Date.valueOf("2020-01-01"), role, null);
        owningUser = userRepository.save(owningUser);

        post = new Post(null, "post content", false, owningUser, Date.valueOf("2020-01-01"), null, null,null,null);
        post = postRepository.save(post);

        comment = new Comment();
        comment.setContent("comment content");
        comment.setPost(post);
        comment.setOwner(owningUser);
        comment.setCreationDate(Date.valueOf("2020-03-02"));
        comment.setCommentParent(null);
        comment = commentRepository.save(comment);

        childComment1 = new Comment(null, "child comment content1", post, owningUser, Date.valueOf("2020-01-01"), comment);
        childComment2 = new Comment(null, "child comment content2", post, owningUser, Date.valueOf("2020-02-02"), comment);
        childComment1 = commentRepository.save(childComment1);
        childComment2 = commentRepository.save(childComment2);
    }

    @AfterEach
    public void clearTestData(){
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void save_returns_saved_comment_with_id() {
        assertThat(comment.getId()).isNotNull();
        assertThat(childComment1.getId()).isNotNull();
        assertThat(childComment2.getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void findCommentsByPostId_returns_all_comments_for_post(){
        List<Comment> actualComments = commentRepository.findCommentsByPostId(post.getId());

        assertThat(actualComments).hasSize(3);
        assertThat(actualComments.get(0)).satisfies(actualComment -> {
            assertThat(actualComment.getId()).isEqualTo(comment.getId());
            assertThat(actualComment.getContent()).isEqualTo(comment.getContent());
            assertThat(actualComment.getPost().getId()).isEqualTo(comment.getPost().getId());
            assertThat(actualComment.getOwner().getUserId()).isEqualTo(comment.getOwner().getUserId());
            assertThat(actualComment.getCreationDate()).isEqualTo(comment.getCreationDate());
            assertThat(actualComment.getCommentParent()).isEqualTo(comment.getCommentParent());
        });

        assertThat(actualComments.get(1)).satisfies(actualComment -> {
            assertThat(actualComment.getId()).isEqualTo(childComment1.getId());
            assertThat(actualComment.getContent()).isEqualTo(childComment1.getContent());
            assertThat(actualComment.getPost().getId()).isEqualTo(childComment1.getPost().getId());
            assertThat(actualComment.getOwner().getUserId()).isEqualTo(childComment1.getOwner().getUserId());
            assertThat(actualComment.getCreationDate()).isEqualTo(childComment1.getCreationDate());
            assertThat(actualComment.getCommentParent().getId()).isEqualTo(childComment1.getCommentParent().getId());
        });

        assertThat(actualComments.get(2)).satisfies(actualComment -> {
            assertThat(actualComment.getId()).isEqualTo(childComment2.getId());
            assertThat(actualComment.getContent()).isEqualTo(childComment2.getContent());
            assertThat(actualComment.getPost().getId()).isEqualTo(childComment2.getPost().getId());
            assertThat(actualComment.getOwner().getUserId()).isEqualTo(childComment2.getOwner().getUserId());
            assertThat(actualComment.getCreationDate()).isEqualTo(childComment2.getCreationDate());
            assertThat(actualComment.getCommentParent().getId()).isEqualTo(childComment2.getCommentParent().getId());
        });
    }

    @Test
    @Order(3)
    public void findCommentsByCommentParentId(){
        List<Comment> actualComments = commentRepository.findCommentsByCommentParentId(comment.getId());
        assertThat(actualComments).hasSize(2);

        assertThat(actualComments.get(0)).satisfies(actualComment -> {
            assertThat(actualComment.getId()).isEqualTo(childComment1.getId());
            assertThat(actualComment.getContent()).isEqualTo(childComment1.getContent());
            assertThat(actualComment.getPost().getId()).isEqualTo(childComment1.getPost().getId());
            assertThat(actualComment.getOwner().getUserId()).isEqualTo(childComment1.getOwner().getUserId());
            assertThat(actualComment.getCreationDate()).isEqualTo(childComment1.getCreationDate());
        });

        assertThat(actualComments.get(1)).satisfies(actualComment -> {
            assertThat(actualComment.getId()).isEqualTo(childComment2.getId());
            assertThat(actualComment.getContent()).isEqualTo(childComment2.getContent());
            assertThat(actualComment.getPost().getId()).isEqualTo(childComment2.getPost().getId());
            assertThat(actualComment.getOwner().getUserId()).isEqualTo(childComment2.getOwner().getUserId());
            assertThat(actualComment.getCreationDate()).isEqualTo(childComment2.getCreationDate());
        });
    }
}
