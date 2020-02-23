package com.sda.microblogging.service;

import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.FollowerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class) // TODO Ask ahmad
class FollowerServiceTest {

    @Mock
    private FollowerRepository followerRepository;

    @InjectMocks
    private FollowerService followerService;


    private User followerUser;
    private User user;

    private Follower expectedFollowerDetail;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setId(2);

        Date creationDate = Date.valueOf("2020-01-01");

        this.followerUser = new User();
        followerUser.setUserId(2);

        followerUser.setUsername("follower");
        followerUser.setEmail("follower@follower.com");
        followerUser.setPassword("password");
        followerUser.setBlocked(false);
        followerUser.setPrivate(false);
        followerUser.setAvatar("avatar");
        followerUser.setRole(role);
        followerUser.setCreationDate(creationDate);

        this.user = new User();
        user.setUserId(1);
        user.setUsername("User");
        user.setPassword("password");
        user.setEmail("user@user.com");
        user.setBlocked(false);
        user.setPrivate(false);
        user.setAvatar("avatar");
        user.setRole(role);
        user.setCreationDate(creationDate);


        this.expectedFollowerDetail = new Follower();
        expectedFollowerDetail.setId(1);
        expectedFollowerDetail.setFollower(followerUser);
        expectedFollowerDetail.setUser(user);
        expectedFollowerDetail.setFollowingDate(creationDate);
    }

    @Test
    public void followUser_should_return_saved_follower_and_followed_users() {
        when(followerRepository.save(any(Follower.class))).thenReturn(expectedFollowerDetail);
        when(followerRepository.findFollowerByUserAndFollower(anyInt(), anyInt())).thenReturn(Optional.empty());

        Follower actualFollowerDetail = followerService.followUser(expectedFollowerDetail);

        assertEquals(expectedFollowerDetail.getFollower().getUserId(), actualFollowerDetail.getFollower().getUserId());
        assertEquals(expectedFollowerDetail.getUser().getUserId(), actualFollowerDetail.getUser().getUserId());
    }

    @Test
    public void getSpecificFollowerByUserAndFollowerId_with_valid_id_should_return_a_follower() {
        Integer userId = user.getUserId();
        Integer followerId = followerUser.getUserId();

        when(followerRepository.findFollowerByUserAndFollower(anyInt(), anyInt())).thenReturn(Optional.of(expectedFollowerDetail));
        Optional<Follower> specificFollower = followerService.getFollowerByUserIdAndFollowerId(userId, followerId);

        assertThat(specificFollower).containsSame(expectedFollowerDetail);
        assertEquals(specificFollower.get().getId(), expectedFollowerDetail.getId());
    }

    @Test
    public void getAllFollowersByUserId_with_valid_id_should_return_list_of_followers() {
        Integer userId = expectedFollowerDetail.getUser().getUserId();

        when(followerRepository.findAllFollowersByUser(anyInt())).thenReturn(Collections.singletonList(expectedFollowerDetail));
        List<Follower> followers = followerService.getAllFollowersByUserId(userId);

        assertEquals(followers.size(), 1);
        assertThat(followers).contains(expectedFollowerDetail);
    }

    @Test
    public void getAllFollowingByFollowerId_with_valid_id_should_return_list_of_following() {
        Integer followerId = expectedFollowerDetail.getFollower().getUserId();

        when(followerRepository.findAllFollowingByFollower(anyInt())).thenReturn(Collections.singletonList(expectedFollowerDetail));
        List<Follower> following = followerService.getAllFollowingByFollowerId(followerId);

        assertEquals(following.size(), 1);
        assertThat(following).contains(expectedFollowerDetail);

    }

    @Test
    public void unFollowUser_with_valid_userAndFollowerId_should_remove_following() {
        Integer followerId = followerUser.getUserId();
        Integer userId = user.getUserId();

        when(followerRepository.findFollowerByUserAndFollower(userId, followerId)).thenReturn(Optional.of(expectedFollowerDetail));

        followerService.unFollowUser(userId, followerId);
        verify(followerRepository, times(1)).deleteByUserAndFollower(userId, followerId);
    }

    @Test
    public void followUser_if_user_is_already_followed_throws_exception() {
        when(followerRepository.findFollowerByUserAndFollower(anyInt(), anyInt())).thenReturn(Optional.of(expectedFollowerDetail));

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> {
                    followerService.followUser(expectedFollowerDetail);
                });
        assertEquals("User already followed", exception.getMessage());
    }

    @Test
    public void unfollowUser_if_user_is_not_followed_throws_exception() {
        Integer userId = user.getUserId();
        Integer followerId = followerUser.getUserId();

        when(followerRepository.findFollowerByUserAndFollower(anyInt(), anyInt())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> {
                    followerService.unFollowUser(userId, followerId);
                });

        assertEquals("User has been already unfollowed", exception.getMessage());

    }
}