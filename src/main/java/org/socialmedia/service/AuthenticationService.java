package org.socialmedia.service;

import org.socialmedia.dto.LoginResponseDTO;
import org.socialmedia.model.User;

public interface AuthenticationService {

    User registerUser(String username, String password);

    LoginResponseDTO loginUser(String username, String password);

    LoginResponseDTO changePassword(String originalPassword, String newPassword);
}
