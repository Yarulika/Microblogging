package com.sda.microblogging.controller;

import com.sda.microblogging.entity.*;
import com.sda.microblogging.entity.DTO.post.PostSaveDTO;
import com.sda.microblogging.entity.DTO.post.PostDTO;
import com.sda.microblogging.entity.DTO.post.PostShareDTO;
import com.sda.microblogging.entity.mapper.PostMapper;
import com.sda.microblogging.service.PostLikeService;
import com.sda.microblogging.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/microblogging/v1/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostLikeService postLikeService;

    @ApiOperation(value = "Get all public posts", notes = "Get all public posts: isPrivate=false")
    @GetMapping("/allPublic")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public List<PostDTO> findAllPublicPosts() {
        return postService.findAllPostsBasedOnPrivacy(false).parallelStream().map(post -> {
             PostDTO postDTO = postMapper.convertPostToPostSharedDTO(post);
             postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(post.getId()));
             postDTO.setPostLiked(postLikeService.checkIfThePostIsLiked(post.getOwner(),post));
             return postDTO;
        }).collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all my followings and public posts", notes = "Get all my followings and public posts")
    @GetMapping("/byUserId/{userId}")
    @ResponseBody
    public List<PostDTO> findAllMyFollowingsAndPublicPosts(@PathVariable @NotNull int userId) {
        return postService.findAllPostsAndMyFollowingsPost(userId).parallelStream().map(post -> {
            PostDTO postDTO = postMapper.convertPostToPostSharedDTO(post);
            postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(post.getId()));
            postDTO.setPostLiked(postLikeService.checkIfThePostIsLiked(post.getOwner(),post));
            return postDTO;
        }).collect(Collectors.toList());
    }

    @ApiOperation(value = "Get posts by username", notes = "Get posts by given username")
    @GetMapping("/byUsername/{username}")
    @ResponseBody
    public List<PostDTO> findPostsByUsername(@Valid @NotNull @PathVariable String username) {

        return postService.findPostsByOwnerUsername(username).parallelStream().map(post -> {
            PostDTO postDTO = postMapper.convertPostToPostSharedDTO(post);
            postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(post.getId()));
            postDTO.setPostLiked(postLikeService.checkIfThePostIsLiked(post.getOwner(), post));
            return postDTO;
        }).collect(Collectors.toList());
    }

    @ApiOperation(value = "Create post", notes = "Create new post")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PostDTO save(@Valid @RequestBody PostSaveDTO postSaveDTO) {
        Post post = postMapper.convertPostSaveDTOtoPost(postSaveDTO);
        //steps to understand, first we need to convert from postSaveDto to post, than to a postDTO or postSharedDTO
//        Post post1 = postService.save(post);
//        PostDTO postDTO = postMapper.convertPostToPostSharedDTO(post1);

        return postMapper.convertPostToPostSharedDTO(postService.save(post));
    }

    @ApiOperation(value = "Share post", notes = "Share post")
    @PostMapping("/share")
    @ResponseBody
    public PostDTO sharePost(@Valid @RequestBody PostSaveDTO postSaveDTO) {
        return postMapper.convertPostToPostSharedDTO(postService.save(postMapper.convertPostSaveDTOtoPost(postSaveDTO)));
    }

    @ApiOperation(value = "Like post", notes = "Create post like")
    @PostMapping("/like")
    @ResponseBody
    public void likePost(@Valid @RequestBody PostLike postLike) {
        postLikeService.togglePostLike(postLike);
    }
}
