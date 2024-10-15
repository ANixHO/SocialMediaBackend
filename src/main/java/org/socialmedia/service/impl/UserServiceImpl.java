package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.UserException;
import org.socialmedia.model.User;
import org.socialmedia.repository.UserRepository;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Name: [" + username + "] is not valid"));
    }

    public void deleteUser(User user) {
        isOwner(user);
        userRepository.deleteById(user.getUserId());
    }

    public User updateUser(User user) {
        isOwner(user);
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserException("Cannot find user")
        );
    }

    public boolean isOwner(User user) {
        User currUser = getCurrUser();
        if(!currUser.equals(user)){
            throw new UserException("Invalid User");
        }
        return true;
    }

    public User getCurrUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
