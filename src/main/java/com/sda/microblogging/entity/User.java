package com.sda.microblogging.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull
    @Size(min = 1, max = 45)
    @Column
    private String username;

    @NotNull
    @Size(min = 1, max = 45)
    @Column
    private String password;

    @NotNull
    @Size(min = 1, max = 45)
    @Column
    private String email;

    @Column(name = "is_private")
    private boolean isPrivate;

    @NotNull
    @Column
    private String avatar;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @NotNull
    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "blocked_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "blocked_user_id")}
    )
    private Set<User> blockedUsers;

//    @OneToMany(mappedBy = "id")
//    private Set<Follower> followers;
}
