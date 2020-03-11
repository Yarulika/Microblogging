package com.sda.microblogging.service;

import com.sda.microblogging.entity.Client;
import com.sda.microblogging.entity.GrantedAuthorityImplementation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.io.Serializable;
import java.util.*;

/**
 * @author Ahmad Hamouda
 */
public class ClientDetailsImplementation implements ClientDetails, Serializable {

    //Grant SCOPE related to authorities
    private static final String ACCESS_READ = "read";
    private static final String ACCESS_WRITE = "write";
    //TODO: future work related to token expires to be configured
    private static final Integer TOKEN_EXPIRY_TIME = 21600;
    private static final Integer REFRESH_TOKEN_EXPIRY_TIME = 2592000;
    private final Client client;

    public ClientDetailsImplementation(Client client) {
        this.client = client;
    }

    @Override
    public String getClientId() {
        return client.getClientId();
    }

    public int getId() {
        return client.getId();
    }

    @Override
    public Set<String> getResourceIds() {
        return new TreeSet<>();
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    //TODO: future work related to authorities
    public Set<String> getScope() {
        Set<String> grantTypes = new TreeSet<>();
        grantTypes.add(ACCESS_READ);
        grantTypes.add(ACCESS_WRITE);
        return grantTypes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> grantTypes = new TreeSet<>();
        String[] authorities = client.getGrantedAuthority().split(",");
        for (String authority : authorities) {
            grantTypes.add(authority);
        }
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return new TreeSet<>();
    }

    @Override
    //TODO: future work related to authorities
    //TODO: set Machine to Machine allowed privileges [Client privileges] static her till solve it [user wrapper idea], after depend on privileges instead of roles
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<>();
        result.add(new GrantedAuthorityImplementation("ROLE_ADMIN"));
        return result;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return TOKEN_EXPIRY_TIME;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return REFRESH_TOKEN_EXPIRY_TIME;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String, Object> additionalInformation = new TreeMap<>();
        additionalInformation.put("applicationId", client.getName());
        return additionalInformation;
    }

}
