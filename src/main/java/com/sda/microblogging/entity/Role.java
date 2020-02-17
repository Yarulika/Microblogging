package com.sda.microblogging.entity;

import com.sda.microblogging.common.RoleTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // TODO change name to title
    @Enumerated(EnumType.STRING)
    private RoleTitle title;
}
