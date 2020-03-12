package com.sda.microblogging.service;

import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.User;
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
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return followerRepository.findAllFollowersByUser(user.get());
        } else {
            throw new UserNotFoundException();
        }
    }

    public int countFollowersByUserId (Integer userId){
        return getAllFollowersByUserId(userId).size();
    }

    public List<Follower> getAllFollowingByFollowerId(Integer followerId) {
        Optional<User> follower = userRepository.findById(followerId);
        if (follower.isPresent()){
            return followerRepository.findAllFollowingByFollower(follower.get());
        }
        else {
            throw new UserNotFoundException();
        }
    }

    public int countFollowingByFollowerId (Integer followerId){
        return getAllFollowingByFollowerId(followerId).size();
    }


    public Optional<Follower> getFollowerByUserIdAndFollowerId(Integer userId, Integer followerId) {
        return followerRepository.findFollowerByUserAndFollower(userId, followerId);
    }
}
