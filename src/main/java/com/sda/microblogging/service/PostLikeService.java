package com.sda.microblogging.service;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.PostLike;
import com.sda.microblogging.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
@Service
public class PostLikeService {

    @Autowired
    PostLikeRepository postLikeRepository;

    public void togglePostLike(@NotNull PostLike postLike) {

        if (!postLikeRepository.findByPostAndUser(postLike.getPost(),postLike.getUser()).isPresent()){
            postLikeRepository.save(postLike);
        }else
            postLikeRepository.delete(postLike);
    }

    public Integer getNumberOfLikesByPost(@NotNull Post post) {
        return postLikeRepository.countByPost(post);
    }

    public List<PostLike> findPostLikesAndUserByPost(@NotNull Post post) {
        return postLikeRepository.findByPost(post);
    }
}