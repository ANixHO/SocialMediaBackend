package org.socialmedia.service.impl;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.socialmedia.model.Post;
import org.socialmedia.model.Image;
import org.socialmedia.repository.ImageRepository;
import org.socialmedia.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;


    @Override
    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public List<Image> getPostImages(Post post) {
        return imageRepository.findByPost(post);
    }


    @Override
    @Transactional
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveImage(Post post, MultipartFile imageFile, int order) throws IOException {
        Image image = new Image();
        image.setPost(post);
        image.setImage(
                new Binary(BsonBinarySubType.BINARY, imageFile.getBytes())
        );
        image.setOrder(order);
        imageRepository.save(image);
    }
}

