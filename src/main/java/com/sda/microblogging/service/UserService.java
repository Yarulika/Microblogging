package com.sda.microblogging.service;

import com.sda.microblogging.entity.User;
import com.sda.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username){
        return Optional.of(userRepository.findByUsername(username));
    }

    public Optional<User> getById(Integer userId) {
        return userRepository.findById(userId);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
