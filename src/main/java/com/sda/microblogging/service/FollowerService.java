package com.sda.microblogging.service;

import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.UserAlreadyFollowedException;
import com.sda.microblogging.exception.UserAlreadyNotFollowedException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.repository.FollowerRepository;
import com.sda.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    private FollowerRepository followerRepository;
    private UserRepository userRepository;

    @Autowired
    public FollowerService(FollowerRepository followerRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.followerRepository = followerRepository;
    }

    public Follower followUser(@Valid @NotNull Follower follower) {
        if (followerRepository.findFollowerByUserUserIdAndFollowerUserId(follower.getUser().getUserId(), follower.getFollower().getUserId()).isPresent()) {
            throw new UserAlreadyFollowedException();
        }
        return followerRepository.save(follower);
    }

    public void unFollowUser(Integer userId, Integer followerId) {
        if (!followerRepository.findFollowerByUserUserIdAndFollowerUserId(followerId, userId).isPresent()) {
            throw new UserAlreadyNotFollowedException();
        }
        followerRepository.deleteByUserUserIdAndFollowerUserId(followerId, userId);
    }

    public List<Follower> getAllFollowersByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return followerRepository.findAllFollowersByUser(user);
    }

    public int countFollowersByUserId(Integer userId) {
        return getAllFollowersByUserId(userId).size();
    }

    public List<Follower> getAllFollowingByFollowerId(Integer followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(UserNotFoundException::new);
        return followerRepository.findAllFollowingByFollower(follower);
    }

    public int countFollowingByFollowerId(Integer followerId) {
        return getAllFollowingByFollowerId(followerId).size();
    }

    public Optional<Follower> getFollowerByUserIdAndFollowerId(Integer userId, Integer followerId) {
        return followerRepository.findFollowerByUserUserIdAndFollowerUserId(userId, followerId);
    }

    public List<Follower> getAllFollowingByFollowerUsername(String username) {
        return followerRepository.findFollowerByFollowerUsername(username);
    }
}
