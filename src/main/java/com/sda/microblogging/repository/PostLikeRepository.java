package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.PostLike;
import com.sda.microblogging.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {

    Optional<PostLike> findByPostAndUser(Post post, User user);
    Integer countByPost(Post post);
    List<PostLike> findByPost(Post post);
}
