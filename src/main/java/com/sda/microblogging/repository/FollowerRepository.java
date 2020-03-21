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

    List<Follower> findFollowerByFollowerUsername(String username);

    List<Follower> findFollowerByUserUsername(String username);

    // TODO replace with Optional<Follower> findFollowerByUserAndFollower(User user, User follower);
    Optional<Follower> findFollowerByUserUserIdAndFollowerUserId(Integer userId, Integer followerId);

    // TODO replace with void deleteByUserAndFollower(User user, User follower);
    void deleteByUserUserIdAndFollowerUserId(Integer userId, Integer followerId);
}
