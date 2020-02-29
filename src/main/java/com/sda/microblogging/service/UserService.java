package com.sda.microblogging.service;

import com.sda.microblogging.entity.Role;
import com.sda.microblogging.entity.User;
import com.sda.microblogging.exception.UserDetailsFoundException;
import com.sda.microblogging.exception.UserNotFoundException;
import com.sda.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

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

    public String saveImage(byte[] imageData) {
        String imgUrl = new String();
        // TODO hardcoded at the moment: will implement later saving img on hard disc or some media server
        imgUrl = "//https://cdn.pixabay.com/photo/2018/05/31/15/06/not-hear-3444212__340.jpg";
        return imgUrl;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User p = userRepository.findByUsername(s).orElseThrow(() -> new UserNotFoundException());

        return new org.springframework.security.core.userdetails.User(p.getUsername(), p.getPassword(), getRoles(Collections.singletonList(p.getRole())));
    }

    private Collection<? extends GrantedAuthority> getRoles(List<Role> roles){
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getTitle().name())).collect(Collectors.toList());
    }


}
