package com.sda.microblogging.entity.mapper;
import com.sda.microblogging.entity.DTO.post.PostSaveDTO;
import com.sda.microblogging.entity.DTO.post.PostDTO;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO convertPostToDTO(Post post){
        int numberOfPostLikes = 0;
        if (post.getLikes() != null) {
            numberOfPostLikes = post.getLikes().size();
        }

        int numberOfComments = 0;
        if (post.getComments() != null) {
            numberOfComments = post.getComments().size();
        }

        return PostDTO.builder()
                .username(post.getOwner().getUsername())
                .userId(post.getOwner().getUserId())
                .isUserPrivate(post.getOwner().isPrivate())
                .userRole(post.getOwner().getRole().getTitle())
                .avatar(post.getOwner().getAvatar())
                .numberOfPostLikes(numberOfPostLikes)
                .postId(post.getId())
                .content(post.getContent())
                .isPostEdited(post.getIsEdited())
                .postCreatedDate(post.getCreationDate())
                .numberOfComments(numberOfComments)
                .build();
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
