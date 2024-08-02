package org.socialmedia.utils;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    @Autowired
    private JwtUtil jwtUtil;

    public Long getCurrentUserId(){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Claims claims = jwtUtil.extractAllClaims(token);
        return Long.parseLong(claims.get("userId").toString());
    }

    public boolean isAdmin(){
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Claims claims = jwtUtil.extractAllClaims(token);
        return Boolean.parseBoolean(claims.get("isAdmin").toString());
    }
}
