package com.sda.microblogging.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity(name = "users")
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

    @OneToMany(mappedBy = "id")
    private Set<Follower> followers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isPrivate() == user.isPrivate() &&
                isBlocked() == user.isBlocked() &&
                getUserId().equals(user.getUserId()) &&
                getUsername().equals(user.getUsername()) &&
                getPassword().equals(user.getPassword()) &&
                getEmail().equals(user.getEmail()) &&
                Objects.equals(getAvatar(), user.getAvatar()) &&
                Objects.equals(getCreationDate(), user.getCreationDate()) &&
                Objects.equals(getRole(), user.getRole()) &&
                Objects.equals(getBlockedUsers(), user.getBlockedUsers()) &&
                Objects.equals(getFollowers(), user.getFollowers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getPassword(), getEmail(), isPrivate(), getAvatar(), isBlocked(), getCreationDate(), getRole(), getBlockedUsers(), getFollowers());
    }
}
