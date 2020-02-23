package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    List<Follower> findAllFollowersByUser(Integer userId);

    List<Follower> findAllFollowingByFollower(Integer followerId);

    Optional<Follower> findFollowerByUserAndFollower(Integer userId, Integer followerId);

    void deleteByUserAndFollower(Integer userId, Integer followerId);
}

