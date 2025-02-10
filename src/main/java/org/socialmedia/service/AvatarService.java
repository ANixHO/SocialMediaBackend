package org.socialmedia.service;

import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface AvatarService {

    Avatar saveAvatar(MultipartFile multipartFile, String userId) throws IOException;

    Avatar getAvatar(String id);

    Avatar getAvatarByUserId(String userId);

    void deleteAvatar(String id);

    void deleteAvatarByUserId(String userId);

    Avatar updateAvatar(MultipartFile avatarFile, String userId) throws IOException;
}
