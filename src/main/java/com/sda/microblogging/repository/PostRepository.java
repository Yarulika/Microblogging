package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findPostsByOwner(int userId);
    List<Post> findByOrderByCreationDate();

    @Query("select count(p.original_post_id) as numberOfShares from posts p where p.original_post_id = ?1 group by(p.original_post_id);")
    Integer findNumberOfSharesOfPost(int postId);
}
