package org.socialmedia.service.impl;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.socialmedia.repository.mongodb.AvatarRepository;
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

    @Override
    @Transactional
    public Avatar saveAvatar(MultipartFile avatarFile, String userId) throws IOException {
      Avatar avatar = new Avatar();
      avatar.setUserId(userId);
      avatar.setAvatar(
              new Binary(BsonBinarySubType.BINARY, avatarFile.getBytes())
      );
      return avatarRepository.save(avatar);

    }

    @Override
    public Avatar getAvatar(String id) {
        Optional<Avatar> opAvatar = avatarRepository.findById(id);
        return opAvatar.orElse(null);
    }

    @Override
    public Avatar getAvatarByUserId(String userId) {
        return avatarRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAvatar(String id)  {
        avatarRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAvatarByUserId(String userId) {
        avatarRepository.deleteByUserId(userId);
    }

    @Transactional
    public Avatar updateAvatar(MultipartFile avatarFile, String userId) throws IOException {
        deleteAvatarByUserId(userId);
        return saveAvatar(avatarFile, userId);
    }
}
