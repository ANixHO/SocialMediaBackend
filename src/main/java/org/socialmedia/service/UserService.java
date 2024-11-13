package org.socialmedia.service;


import org.socialmedia.dto.UserInfoDTO;
import org.socialmedia.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public User getUser(String id);

    void deleteUser(String userId);

    UserInfoDTO updateUser(UserInfoDTO user);

    boolean isOwner(String userId);

    User getCurrUser();

    UserInfoDTO getUserInfo(String userId);

    UserInfoDTO convertToUserInfoDTO(User user);
}
