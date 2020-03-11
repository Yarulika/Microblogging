package com.sda.microblogging.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Ahmad Hamouda
 */
@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "internal_id")
    private String internalId;

    @Column
    private String name;

    @Column(name = "client_secret")
    @ToString.Exclude
    private String clientSecret;

    @Column(name = "granted_authority")
    private String grantedAuthority;

    public Client(String name) {
        this.name = name;
        clientId = UUID.randomUUID().toString();
        clientSecret = UUID.randomUUID().toString();
        grantedAuthority = "ROLE_CLIENT";
    }

    public void resetCredentials() {
        clientId = UUID.randomUUID().toString();
        clientSecret = UUID.randomUUID().toString();
    }
}