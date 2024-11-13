package org.socialmedia.service.impl;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.socialmedia.model.Post;
import org.socialmedia.model.PostImage;
import org.socialmedia.repository.mongodb.PostImageRepository;
import org.socialmedia.service.PostImageService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostImageServiceImpl implements PostImageService {

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private UserService userService;

    @Override
    public Optional<PostImage> getPostImage(String id) {
        return postImageRepository.findById(id);
    }

    @Override
    public List<PostImage> getPostImages(String postId) {
        return postImageRepository.findByPost(postId);
    }

    @Override
    public PostImage getInitPostImage(String postId){
        return postImageRepository.findFirstByPostIdOrderByOrdersAsc(postId);
    }

    @Override
    public PostImage getLastPostImage(String postId){
        return postImageRepository.findFirstByPostIdOrderByOrdersDesc(postId);
    }

    @Override
    @Transactional
    public void deletePostImage(String postImageId) {
        postImageRepository.deleteById(postImageId);
    }

    @Transactional
    @Override
    public void saveMultiplePostImages(String postId, List<MultipartFile> imageFiles, int lastOrder) throws IOException {

        for (int order = 0 ; order < imageFiles.size() ; order ++){
            int curOrder = lastOrder + order + 1;
            savePostImage(postId, imageFiles.get(order) , curOrder);

        }
    }

    @Override
    @Transactional
    public void savePostImage(String postId, MultipartFile imageFile, int order) throws IOException {
        PostImage postImage = new PostImage();
        postImage.setPostId(postId);
        postImage.setImage(
                new Binary(BsonBinarySubType.BINARY, imageFile.getBytes())
        );
        postImage.setOrders(order);
        postImageRepository.save(postImage);
    }
}

