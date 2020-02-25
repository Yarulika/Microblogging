package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findPostsByOwner(int userId);
    List<Post> findByOrderByCreationDate();
    int countByOriginalPostId(int postId);
}
