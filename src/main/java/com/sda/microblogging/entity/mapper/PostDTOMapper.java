package com.sda.microblogging.entity.mapper;
import com.sda.microblogging.entity.dto.post.PostDTO;
import com.sda.microblogging.entity.Post;

public class PostDTOMapper {


    public PostDTO convertPostToDTO(Post post){
        PostDTO postDTO = new PostDTO();

        postDTO.setUsername(post.getOwner().getUsername());
        postDTO.setUserId(post.getOwner().getUserId());
        postDTO.setUserPrivate(post.getOwner().isPrivate());
        postDTO.setUserRole(post.getOwner().getRole().getTitle());
        postDTO.setAvatar(post.getOwner().getAvatar());
        postDTO.setNumberOfPostLikes(post.getLikes().size());
        postDTO.setPostId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setPostEdited(post.getIsEdited());
        postDTO.setPostCreatedDate(post.getCreationDate());
        postDTO.setNumberOfComments(post.getComments().size());
        return postDTO;
    }

}
