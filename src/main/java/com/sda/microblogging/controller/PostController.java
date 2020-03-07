package com.sda.microblogging.controller;

import com.sda.microblogging.entity.*;
import com.sda.microblogging.entity.DTO.post.PostSaveDTO;
import com.sda.microblogging.entity.DTO.post.PostDTO;
import com.sda.microblogging.entity.mapper.PostMapper;
import com.sda.microblogging.service.PostLikeService;
import com.sda.microblogging.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostLikeService postLikeService;

    @GetMapping("/public/post")
    public @ResponseBody
    List<PostDTO> findAllPublicPosts(){
        return postService.findAllPostsBasedOnPrivacy(false).parallelStream().map(post -> {
             PostDTO postDTO = postMapper.convertPostToDTO(post);
             postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(post.getId()));
             postDTO.setPostLiked(postLikeService.checkIfThePostIsLiked(post.getOwner(),post));
             return postDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public @ResponseBody
    List<PostDTO> findAllMyFollowingsAndPublicPosts(@PathVariable @NotNull int userId){
        return postService.findAllPostsAndMyFollowingsPost(userId).parallelStream().map(post -> {
            PostDTO postDTO = postMapper.convertPostToDTO(post);
            postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(post.getId()));
            postDTO.setPostLiked(postLikeService.checkIfThePostIsLiked(post.getOwner(),post));
            return postDTO;
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/post/create",method = RequestMethod.POST)
    public @ResponseBody Post save(@Valid @RequestBody PostSaveDTO postSaveDTO){
        return postService.save(postMapper.convertDtoToPost(postSaveDTO));
    }

    @RequestMapping(value = "/post/share",method = RequestMethod.POST)
    @ResponseBody
    public Post sharePost(@Valid @RequestBody PostSaveDTO postSaveDTO){
        return postService.save(postMapper.convertDtoToPost(postSaveDTO));
    }

    @RequestMapping(value = "/post/like",method = RequestMethod.POST)
    public @ResponseBody
    void likePost(@Valid @RequestBody PostLike postLike){
        postLikeService.togglePostLike(postLike);
    }

    @GetMapping("/posts/{username}")
    public @ResponseBody
    List<PostDTO> findPostsByUsername(@Valid @NotNull @PathVariable String username){

        return postService.findPostsByOwnerUsername(username).parallelStream().map(post -> {
            PostDTO postDTO = postMapper.convertPostToDTO(post);
            postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(post.getId()));
            postDTO.setPostLiked(postLikeService.checkIfThePostIsLiked(post.getOwner(),post));
            return postDTO;
        }).collect(Collectors.toList());
    }
}
