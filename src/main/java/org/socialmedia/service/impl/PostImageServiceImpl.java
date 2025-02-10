package org.socialmedia.service.impl;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.socialmedia.Exceptions.PostNotFoundException;
import org.socialmedia.dto.PostImageDTO;
import org.socialmedia.model.PostImage;
import org.socialmedia.repository.mongodb.PostImageRepository;
import org.socialmedia.service.PostImageService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    public List<PostImage> getPostImagesByPostId(String postId) {
        return postImageRepository.findByPostId(postId);
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
    public void deletePostImagesByPostId(String postId){
        postImageRepository.deleteByPostId(postId);
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

    public PostImageDTO getPostImageDTOById(String id){
        PostImage postImage = getPostImage(id).orElseThrow(() -> new PostNotFoundException("Post image, id: " + id + " not found"));
        return convertToDto(postImage);
    }

    public PostImageDTO getInitPostImageDTOByPostId(String postId){
        return convertToDto(getInitPostImage(postId));
    }

    public List<PostImageDTO> getPostImageDTOsByPostId(String postId){
        List<PostImage> list = getPostImagesByPostId(postId);
        list.sort(Comparator.comparing(PostImage::getOrders));
        List<PostImageDTO> dtos = new ArrayList<>();
        for (PostImage postImage : list) {
            dtos.add(convertToDto(postImage));
        }
        return dtos;

    }

    private PostImageDTO convertToDto(PostImage postImage){
        PostImageDTO dto = new PostImageDTO();
        dto.setId(postImage.getId());
        dto.setImageBinary(postImage.getImage());
        return dto;
    }
}

