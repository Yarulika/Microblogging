package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentsByPostId(int postId);

    List<Comment> findCommentsByCommentParentId(int commentParentId);

}
