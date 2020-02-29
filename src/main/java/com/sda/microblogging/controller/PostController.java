package com.sda.microblogging.controller;

import com.sda.microblogging.entity.DTO.post.NewPostDTO;
import com.sda.microblogging.entity.DTO.post.PostDTO;
import com.sda.microblogging.entity.Follower;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.entity.mapper.PostDTOMapper;
import com.sda.microblogging.service.FollowerService;
import com.sda.microblogging.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostDTOMapper postDTOMapper;
    @Autowired
    private FollowerService followerService;

    @GetMapping("/{postId}")
    public @ResponseBody
    PostDTO findPostById(@PathVariable @NotBlank int postId){
        PostDTO postDTO = postDTOMapper.convertPostToDTO(postService.findPostById(postId).get());
        postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(postId));
        return postDTO;
    }

    @GetMapping
    public @ResponseBody
    List<PostDTO> findAllPublicPosts(){
        return postService.findAllPostsBasedOnPrivacy(false).parallelStream().map(post -> {
             PostDTO postDTO = postDTOMapper.convertPostToDTO(post);
             postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(post.getId()));
             return postDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public @ResponseBody
    List<PostDTO> findAllMyFollowingsAndPublicPosts(@PathVariable @NotNull int userId){

        //TODO list the post based on my followings and public ons

        return findAllPublicPosts();
    }

    @RequestMapping(value = "/post/create",method = RequestMethod.POST)
    @ResponseBody
    public Post save(@Valid @RequestBody NewPostDTO newPostDTO){
        return postService.save(postDTOMapper.convertDtoToPost(newPostDTO));
    }

    @RequestMapping(value = "/post/share",method = RequestMethod.POST)
    @ResponseBody
    public Post sharePost(@Valid @RequestBody NewPostDTO newPostDTO){
        return postService.save(postDTOMapper.convertDtoToPost(newPostDTO));
    }
}
