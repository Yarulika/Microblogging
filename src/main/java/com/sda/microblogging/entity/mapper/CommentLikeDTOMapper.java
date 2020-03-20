package com.sda.microblogging.entity.mapper;

import com.sda.microblogging.entity.Comment;
import com.sda.microblogging.entity.CommentLike;
import com.sda.microblogging.entity.User;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CommentLikeDTOMapper {

    public CommentLike toCommentLike(Comment comment, User user) {
        long millis = System.currentTimeMillis();
        Date likedDate = new Date(millis);

        return CommentLike.builder()
                .comment(comment)
                .user(user)
                .likedDate(likedDate)
                .build();
    }
}
