package org.socialmedia.service;

import org.socialmedia.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long id);

    User setUserAsAdmin(Long userId);
}
