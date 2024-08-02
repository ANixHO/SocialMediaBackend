package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.UserAlreadyExistException;
import org.socialmedia.Exceptions.UserNotFoundException;
import org.socialmedia.model.User;
import org.socialmedia.repository.UserRepository;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createUser(User user) {
        try {
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Password must not be empty");
            }

            user.setPasswordHash(passwordEncoder.encode(user.getPassword()));

            user.setPassword(null);
            user.setAdmin(false);

            user.setCreatedAt(LocalDateTime.now());
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("A user with this username or email already exists", e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User setUserAsAdmin(Long userId){
        User user = getUserById(userId);
        user.setAdmin(true);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with user name: " + username);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        if (!userRepository.existsById((user.getId()))) {
            throw new UserNotFoundException("Can not update. User not found with id: " + user.getId());
        }

        try {
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Can not update. A user with this user name or email already exists", e);
        }
    }

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("Can not delete. User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

}
