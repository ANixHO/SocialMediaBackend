package org.socialmedia.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    public String generateJwt(Authentication authentication);
}
