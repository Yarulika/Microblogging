package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    List<Follower> findAllFollowersByUser(User user);

    List<Follower> findAllFollowingByFollower(User follower);

    List<Follower> findFollowingByFollowerUsername(String username);

    List<Follower> findFollowerByUserUsername(String username);

    Optional<Follower> findFollowerByUserUserIdAndFollowerUserId(Integer userId, Integer followerId);

    void deleteByUserUserIdAndFollowerUserId(Integer userId, Integer followerId);
}
