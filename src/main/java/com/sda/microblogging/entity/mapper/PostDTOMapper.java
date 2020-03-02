package com.sda.microblogging.entity.mapper;
import com.sda.microblogging.entity.DTO.post.PostSaveDTO;
import com.sda.microblogging.entity.DTO.post.PostDTO;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostDTOMapper {
    @Autowired
    UserService userService;

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

    public Post convertDtoToPost(PostSaveDTO postSaveDTO){
        Post post = new Post();

        post.setOriginalPost(postSaveDTO.getOriginalPost());
        post.setCreationDate(postSaveDTO.getCreationDate());
        post.setOwner(postSaveDTO.getOwner());
        post.setContent(postSaveDTO.getContent());
        post.setIsEdited(false);
        post.setOriginalPost(postSaveDTO.getOriginalPost());
        return post;
    }
}
