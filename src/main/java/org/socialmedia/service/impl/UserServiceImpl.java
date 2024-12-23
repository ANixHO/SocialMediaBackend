package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.UserException;
import org.socialmedia.dto.UserInfoDTO;
import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.socialmedia.repository.mysql.UserRepository;
import org.socialmedia.service.AvatarService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvatarService avatarService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Name: [" + username + "] is not valid"));
    }

    public void deleteUser(String userId) {
        isOwner(userId);
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserInfoDTO updateUser(UserInfoDTO userInfoDTO) {
        isOwner(userInfoDTO.getId());
        User user = userRepository.findById(userInfoDTO.getId()).orElseThrow(
                () -> new UserException("User not found")
        );

        if (userInfoDTO.getUsername() != null) {
            user.setUsername(userInfoDTO.getUsername());
        }

        if (userInfoDTO.getAvatarFile() != null) {
            try {
                Avatar avatar = avatarService.updateAvatar(
                        userInfoDTO.getAvatarFile(), userInfoDTO.getId()
                );
                user.setAvatar(avatar);
                userInfoDTO.setAvatarBinary(avatar.getAvatar());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                userRepository.save(user);
            }
        }

        return userInfoDTO;
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserException("Cannot find user")
        );
    }

    public boolean isOwner(String userId) {
        User currUser = getCurrUser();
        if (currUser.getId().equals(userId)) {
            return true;
        } else {
            throw new UserException("Current user is not the owner of the content");
        }
    }

    public User getCurrUser() {
        User curUser = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) auth.getPrincipal();
            String curUserId = jwt.getClaimAsString("sub");
            curUser = userRepository.findById(curUserId).orElse(null);
        }

        return curUser;
    }

    public UserInfoDTO getUserInfo(String userId) {
        User user = getUser(userId);
        return convertToUserInfoDTO(user);
    }

    public UserInfoDTO convertToUserInfoDTO(User user) {
        UserInfoDTO dto = new UserInfoDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        Avatar avatar = avatarService.getAvatarByUserId(user.getId());
        dto.setAvatarBinary(avatar.getAvatar());
        return dto;
    }



}
