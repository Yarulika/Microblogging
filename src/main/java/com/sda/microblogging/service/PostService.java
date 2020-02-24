package com.sda.microblogging.service;

import com.sda.microblogging.entity.Post;
import com.sda.microblogging.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findPostsByOwner(int userId) {
        return postRepository.findPostsByOwner(userId);
    }

    public Post save(Post post) {

        requireNonNull(post.getContent());//should have a content
        if (post.getContent().trim().isEmpty()){
            postRepository.save(post);
        }else {
            throw new RuntimeException("Post should have a content");
        }
        return postRepository.save(post);
    }

    public List<Post> findByOrderByCreationDate() {
        return postRepository.findByOrderByCreationDate();
    }

    public int findNumberOfSharesOfPost(@NotBlank @Min(1) int postId){
        return postRepository.findNumberOfSharesOfPost(postId);
    }

    public Optional<Post> findPostById(@NotBlank @Min(1) int postId){ return postRepository.findById(postId);}

    public Post sharePost(@NotNull Post post) {

        requireNonNull(post.getOriginalPost().getId());//should have the original post id
        Optional<Post> originalPost =  postRepository.findById(post.getOriginalPost().getId());

        if (originalPost.isPresent()){
            return postRepository.save(post);
        }else{
            return post;
        }
    }
}