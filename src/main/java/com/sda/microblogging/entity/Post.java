package com.sda.microblogging.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Entity(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Comparable<Post>{

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Size(min = 1, max = 160)
    private String content;

    @Column(name = "is_edited")
    private Boolean isEdited;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "original_post_id")
    private Post originalPost;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags;

    @Override
    public int compareTo(Post post) {
        return getCreationDate().compareTo(post.creationDate);
    }
}
