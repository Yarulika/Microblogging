package com.sda.microblogging.service;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.PostLike;
import com.sda.microblogging.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

public class PostLikeService {

    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    public PostLike save(@NotNull PostLike postLike) {

        if (userService.findUserById(postLike.getUser().getUserId()).isPresent() && postService.findPostById(postLike.getPost().getId()).isPresent()){
            postLikeRepository.save(postLike);
        }
        return postLikeRepository.save(postLike);
    }

    public void dislikePost(PostLike postLike) {

        if (postLikeRepository.findByPostAndUser(postLike.getPost(),postLike.getUser()).isPresent()){
            postLikeRepository.delete(postLike);
        }
    }
}