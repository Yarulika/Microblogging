package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ahmad Hamouda
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findOneByClientId(String clientId);
}
