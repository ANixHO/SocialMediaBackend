package org.socialmedia.service;
import org.socialmedia.model.PostImage;
import org.socialmedia.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostImageService {
    Optional<PostImage> getPostImage(Long imageId);

    List<PostImage> getPostImages(Post post);

    void deletePostImage(PostImage postImage);

    void savePostImage(Post post, MultipartFile imageFile, int order) throws IOException;

    void saveMultiplePostImages(Post post, List<MultipartFile> imagefiles, int lastOrder) throws IOException;

    PostImage getInitPostImage(Post post);

    PostImage getLastPostImage(Post post);

}
