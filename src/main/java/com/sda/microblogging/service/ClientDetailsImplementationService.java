package com.sda.microblogging.service;

import com.sda.microblogging.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author Ahmad Hamouda
 */
//Overrides the loadClientByClientId to get clientDetails from repository
@Service
public class ClientDetailsImplementationService implements ClientDetailsService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(@NotNull String clientId) {
        return clientRepository.findOneByClientId(clientId)
                .map(ClientDetailsImplementation::new)
                .orElseThrow(() -> new NoSuchClientException("Bad client credentials."));
    }

}
