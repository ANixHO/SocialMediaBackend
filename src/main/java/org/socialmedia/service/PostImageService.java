package org.socialmedia.service;
import org.socialmedia.dto.PostImageDTO;
import org.socialmedia.model.PostImage;
import org.socialmedia.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostImageService {
    Optional<PostImage> getPostImage(String imageId);
    List<PostImage> getPostImagesByPostId(String postId);
    void deletePostImage(String postImageId);
    void deletePostImagesByPostId(String postId);
    void savePostImage(String postId, MultipartFile imageFile, int order) throws IOException;
    void saveMultiplePostImages(String postId, List<MultipartFile> imagefiles, int lastOrder) throws IOException;
    PostImage getInitPostImage(String postId);
    PostImage getLastPostImage(String postId);


    PostImageDTO getPostImageDTOById(String id);
    PostImageDTO getInitPostImageDTOByPostId(String postId);
    List<PostImageDTO> getPostImageDTOsByPostId(String postId);
}
