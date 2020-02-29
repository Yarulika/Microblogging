package com.sda.microblogging.controller;

import com.sda.microblogging.entity.dto.post.PostDTO;
import com.sda.microblogging.entity.mapper.PostDTOMapper;
import com.sda.microblogging.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public @ResponseBody
    PostDTO findAll(){

        PostDTOMapper postDTOMapper = new PostDTOMapper();
        PostDTO postDTO = postDTOMapper.convertPostToDTO(postService.findPostById(1).get());
        postDTO.setNumberOfPostShares(postService.findNumberOfSharesOfPost(1));

        return postDTO;
    }
}
