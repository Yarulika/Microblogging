package com.sda.microblogging.service;

import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.UserDetailsFoundException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(@Valid @NotNull User user) {
        if (findUserByUsername(user.getUsername()).isPresent()){
            throw new UserDetailsFoundException("Person with username: " + user.getUsername() + "already exists");
        }
        else if (findUserByEmail(user.getEmail()).isPresent()){
            throw new UserDetailsFoundException("Person with email: " + user.getEmail() + "already exists");
        }
        return userRepository.save(user);
    }

    public User updateUserPassword(@NotBlank Integer userId, @NotBlank @Size(min = 1, max = 45) String newPassword) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()){
            throw new UserNotFoundException();
        }
        else {
        // TODO after Security:  user.setPassword(passwordEncoder.encode(password));
            user.get().setPassword(newPassword);
            return userRepository.save(user.get());
        }
    }

    public User updateUserPrivacy(@NotBlank String userEmail, boolean isPrivate) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        user.setPrivate(isPrivate);
        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(@NotBlank String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(@NotBlank String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(@NotBlank Integer userId) {
        return userRepository.findById(userId);
    }

    public User update(@Valid @NotNull User updatedUser) {
        if (findUserById(updatedUser.getUserId()).isPresent()) {
            return userRepository.save(updatedUser);
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<User> findAllActiveUsers(){
        return userRepository.findAllByIsBlocked(false);
    }

}
