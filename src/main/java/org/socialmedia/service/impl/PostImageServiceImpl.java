package org.socialmedia.service.impl;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.socialmedia.model.Post;
import org.socialmedia.model.PostImage;
import org.socialmedia.repository.PostImageRepository;
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
    public Optional<PostImage> getPostImage(Long id) {
        return postImageRepository.findById(id);
    }

    @Override
    public List<PostImage> getPostImages(Post post) {
        return postImageRepository.findByPost(post);
    }

    @Override
    public PostImage getInitPostImage(Post post){
        return postImageRepository.findFirstByPostOrderByOrdersAsc(post);
    }

    @Override
    public PostImage getLastPostImage(Post post){
        return postImageRepository.findFirstByPostOrderByOrdersDesc(post);
    }

    @Override
    @Transactional
    public void deletePostImage(PostImage postImage) {
        userService.isOwner(postImage.getPost().getUser());
        postImageRepository.deleteById(postImage.getId());
    }

    @Transactional
    @Override
    public void saveMultiplePostImages(Post post, List<MultipartFile> imageFiles, int lastOrder) throws IOException {

        for (int order = 0 ; order < imageFiles.size() ; order ++){
            int curOrder = lastOrder + order + 1;
            savePostImage(post, imageFiles.get(order) , curOrder);

        }
    }

    @Override
    @Transactional
    public void savePostImage(Post post, MultipartFile imageFile, int order) throws IOException {
        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImage(
                new Binary(BsonBinarySubType.BINARY, imageFile.getBytes())
        );
        postImage.setOrders(order);
        postImageRepository.save(postImage);
    }
}

