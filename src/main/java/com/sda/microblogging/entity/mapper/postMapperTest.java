package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.dto.post.PostDTO;
import com.sda.microblogging.entity.Post;
import com.sda.microblogging.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class postMapperTest extends PostDTO {

    @Autowired
    private Post post;
    @Autowired
    private User user;
    @Autowired
    private int nrOfPostLikes;

    postMapperTest(Post post, User user, int nrOfLikes){
        this.post=post;
        this.user=user;
        this.nrOfPostLikes=nrOfLikes;
    }


}
