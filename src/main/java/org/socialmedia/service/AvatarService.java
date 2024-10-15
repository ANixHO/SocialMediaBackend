package org.socialmedia.service;

import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface AvatarService {

    void saveAvatar(MultipartFile multipartFile, User user) throws IOException;

    Avatar getAvatar(Long id);

    Avatar getAvatarByUser(User user);

    void deleteAvatar(Long id);

    void deleteAvatarByUser(User user);

    void updateAvatar(MultipartFile avatarFile, User user) throws IOException;
}
