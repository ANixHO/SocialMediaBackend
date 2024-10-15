package org.socialmedia.service.impl;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.socialmedia.repository.AvatarRepository;
import org.socialmedia.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    @Transactional
    public void saveAvatar(MultipartFile avatarFile, User user) throws IOException {
      Avatar avatar = new Avatar();
      avatar.setUser(user);
      avatar.setAvatar(
              new Binary(BsonBinarySubType.BINARY, avatarFile.getBytes())
      );
      avatarRepository.save(avatar);

    }

    @Override
    public Avatar getAvatar(Long id) {
        Optional<Avatar> opAvatar = avatarRepository.findById(id);
        return opAvatar.orElse(null);
    }

    @Override
    public Avatar getAvatarByUser(User user) {
        return avatarRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void deleteAvatar(Long id)  {
        avatarRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAvatarByUser(User user) {
        avatarRepository.deleteByUser(user);
    }

    @Transactional
    public void updateAvatar(MultipartFile avatarFile, User user) throws IOException {
        deleteAvatarByUser(user);
        saveAvatar(avatarFile, user);
    }
}
