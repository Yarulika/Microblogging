package com.sda.microblogging.entity.mapper;
import com.sda.microblogging.entity.DTO.post.OriginalPostDTO;
import com.sda.microblogging.entity.DTO.post.PostSaveDTO;
import com.sda.microblogging.entity.DTO.post.PostDTO;
import com.sda.microblogging.entity.DTO.post.PostShareDTO;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.service.PostService;
import com.sda.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    public Post convertPostSaveDTOtoPost(PostSaveDTO postSaveDTO){
        Post post = new Post();
        Post originalPost = null;

        if(postSaveDTO.getOriginalPost() != null){
            originalPost = postService.findPostById(postSaveDTO.getOriginalPost()).get();
        }

        post.setOriginalPost(originalPost);
        post.setCreationDate(postSaveDTO.getCreationDate());
        post.setOwner(userService.findUserById(postSaveDTO.getOwner()).get());
        post.setContent(postSaveDTO.getContent());
        post.setIsEdited(false);
        return post;
    }

    public PostDTO convertPostToPostSharedDTO(Post post){
        PostDTO postShareDTO;
        if (post.getOriginalPost()!=null)
               postShareDTO = buildPostShareDTO(post);
        else{
            postShareDTO = buildPostDTO(post);
        }
        return postShareDTO;
    }

    private PostDTO buildPostDTO(Post post){

        return PostDTO.builder()
                .username(post.getOwner().getUsername())
                .userId(post.getOwner().getUserId())
                .isUserPrivate(post.getOwner().isPrivate())
                .userRole(post.getOwner().getRole().getTitle())
                .avatar(post.getOwner().getAvatar())
                .numberOfPostLikes(numberOfPostLikes(post))
                .postId(post.getId())
                .content(post.getContent())
                .isPostEdited(post.getIsEdited())
                .postCreatedDate(post.getCreationDate())
                .numberOfComments(numberOfComments(post))
                .build();
    }

    private PostShareDTO buildPostShareDTO(Post post){
        //todo need refactoring
        PostShareDTO postShareDTO = new PostShareDTO(post.getOwner().getUsername(),post.getOwner().getUserId(),post.getOwner().isPrivate(),post.getOwner().getRole().getTitle(),post.getOwner().getAvatar(),0,post.getId(),post.getContent(),post.getIsEdited(),post.getCreationDate(),numberOfPostLikes(post),numberOfComments(post),false);
        OriginalPostDTO originalPostDTO = OriginalPostDTO.builder()
                .username(post.getOriginalPost().getOwner().getUsername())
                .userId(post.getOriginalPost().getOwner().getUserId())
                .avatar(post.getOriginalPost().getOwner().getAvatar())
                .content(post.getOriginalPost().getContent())
                .postCreatedDate(post.getOriginalPost().getCreationDate())
                .build();
        postShareDTO.setOriginalPostDTO(originalPostDTO);
        return postShareDTO;
    }
    private int numberOfPostLikes(Post post){
        return post.getLikes()==null? 0:post.getLikes().size();
    }

    private int numberOfComments(Post post) {
        return post.getComments()==null? 0:post.getComments().size();
    }
}
