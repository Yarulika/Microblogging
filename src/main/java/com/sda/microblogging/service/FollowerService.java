package com.sda.microblogging.service;

import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    @Autowired
    FollowerRepository followerRepository;

    public Follower followUser(@Valid @NotNull Follower newFollower) {
        Integer userId = newFollower.getUser().getUserId();
        Integer followerId = newFollower.getFollower().getUserId();

        if (getFollowerByUserIdAndFollowerId(userId, followerId).isPresent()) {
            throw new RuntimeException("User already followed");
        }

        return followerRepository.save(newFollower);
    }

    public void unFollowUser(Integer userId, Integer followerId) {
        if (!getFollowerByUserIdAndFollowerId(userId, followerId).isPresent()) {
            throw new RuntimeException("User has been already unfollowed");
        }

        followerRepository.deleteByUserAndFollower(userId, followerId);
    }

    public List<Follower> getAllFollowersByUserId(Integer userId) {
        return followerRepository.findAllFollowersByUser(userId);
    }

    public List<Follower> getAllFollowingByFollowerId(Integer followerId) {
        return followerRepository.findAllFollowingByFollower(followerId);
    }

    public Optional<Follower> getFollowerByUserIdAndFollowerId(Integer userId, Integer followerId) {
        return followerRepository.findFollowerByUserAndFollower(userId, followerId);
    }
}
