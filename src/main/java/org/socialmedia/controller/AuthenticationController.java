package org.socialmedia.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.socialmedia.Exceptions.UserException;
import org.socialmedia.dto.LoginResponseDTO;
import org.socialmedia.dto.RegistrationDTO;
import org.socialmedia.model.User;
import org.socialmedia.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(),body.getPassword());
    }

    @PutMapping("/changePassword")
    public ResponseEntity<LoginResponseDTO> changePassword(@RequestParam String originalPassword,
                                               @RequestParam String newPassword) {
        LoginResponseDTO dto = authenticationService.changePassword(originalPassword, newPassword);
        return ResponseEntity.ok(dto);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validateToken")
    public ResponseEntity<Void> validateToken(){
        return ResponseEntity.ok().build();
    }

}
