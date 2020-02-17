package com.sda.microblogging.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column
    private String avatar;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "creation_date")
    private Date creationDate;

    // TODO change name in database
    @Column(name = "role")
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "blocked_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "blocked_user_id")}
    )
    private Set<User> blockedUsers;

    @OneToMany(mappedBy = "id")
    private Set<Follower> followers;
}
