package com.sda.microblogging.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ahmad Hamouda
 */
public class GrantedAuthorityImplementation implements GrantedAuthority, Serializable {

    private final String grantedAuthority;

    public GrantedAuthorityImplementation(String grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    @Override
    public String getAuthority() {
        return grantedAuthority;
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o) {
            return true;
        }
        // null check
        if (o == null) {
            return false;
        }
        // type check and cast
        if (!(o instanceof GrantedAuthorityImplementation)) {
            return false;
        }
        GrantedAuthorityImplementation grantedAuthorityImplementation = (GrantedAuthorityImplementation) o;
        // field comparison
        return grantedAuthorityImplementation.getAuthority().equals(this.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.grantedAuthority);
    }

}
