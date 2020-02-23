package com.sda.microblogging.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "post_likes")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "liked_date")
    private Date likedDate;
}
