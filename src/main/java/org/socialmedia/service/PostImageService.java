package org.socialmedia.service;
import org.socialmedia.model.PostImage;
import org.socialmedia.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostImageService {
    Optional<PostImage> getPostImage(String imageId);

    List<PostImage> getPostImages(String postId);

    void deletePostImage(String postImageId);

    void savePostImage(String postId, MultipartFile imageFile, int order) throws IOException;

    void saveMultiplePostImages(String postId, List<MultipartFile> imagefiles, int lastOrder) throws IOException;

    PostImage getInitPostImage(String postId);

    PostImage getLastPostImage(String postId);

}
