package com.sda.microblogging.repository;

import com.sda.microblogging.common.RoleTitle;
import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

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

    @Test
    @Order(1)
    public void save_returns_saved_comment_with_id() {
        Role role = new Role(1, RoleTitle.ADMIN);
        User owningUser;
        Post post;
        Comment comment;
        Comment childComment1;
        Comment childComment2;

        owningUser = new User(null, "ownerUsername", "ownerPassword", "owner@mail.com", true, "?", false, Date.valueOf("2020-01-01"), role, null);
        owningUser = userRepository.save(owningUser);

        post = new Post(null, "post content", false, owningUser, Date.valueOf("2020-01-01"), null, null);
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

        System.out.println("childComment1 " + childComment1);
        System.out.println("childComment2 " + childComment2);

        assertThat(comment.getId()).isNotNull();
        assertThat(childComment1.getId()).isNotNull();
        assertThat(childComment2.getId()).isNotNull();

        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
}
