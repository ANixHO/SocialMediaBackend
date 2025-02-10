package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.UserException;
import org.socialmedia.dto.LoginResponseDTO;
import org.socialmedia.model.Role;
import org.socialmedia.model.User;
import org.socialmedia.repository.mysql.RoleRepository;
import org.socialmedia.repository.mysql.UserRepository;
import org.socialmedia.service.AuthenticationService;
import org.socialmedia.service.TokenService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;


    @Override
    public User registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User user = new User(username, encodedPassword, authorities);

        return userRepository.save(user);
    }

    @Override
    public LoginResponseDTO loginUser(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);
            User user = userRepository.findByUsername(username).get();
            return new LoginResponseDTO(user.getId(), user.getUsername(), token);
        } catch (AuthenticationException e) {
            return new LoginResponseDTO("", "", "");
        }
    }

    public LoginResponseDTO changePassword(String originalPassword, String newPassword) {
        User user = userService.getCurrUser();
        String inputOriginalPassword = passwordEncoder.encode(originalPassword);
        String storedOriginalPassword = user.getPassword();

        if (!inputOriginalPassword.equals(storedOriginalPassword)) {
            throw new UserException("Wrong original password");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), newPassword)
        );

        String newToken = tokenService.generateJwt(auth);
        return new LoginResponseDTO(user.getId(), user.getUsername(), newToken);
    }
}
