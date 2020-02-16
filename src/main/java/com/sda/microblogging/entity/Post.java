package com.sda.microblogging.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

  //TODO contents type change to String
    @Column
    @Max(160)
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

}
