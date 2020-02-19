package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {
}
