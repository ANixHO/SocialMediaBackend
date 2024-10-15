package org.socialmedia.service;


import org.socialmedia.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public User getUser(Long id);

    void deleteUser(User user);

    User updateUser(User user);

    boolean isOwner(User user);

    User getCurrUser();
}
