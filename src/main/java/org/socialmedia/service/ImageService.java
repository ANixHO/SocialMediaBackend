package org.socialmedia.service;
import org.socialmedia.model.Image;
import org.socialmedia.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    Optional<Image> getImage(Long imageId);

    List<Image> getPostImages(Post post);

    void deleteImage(Long imageId);

    void saveImage(Post post, MultipartFile imageFile, int order) throws IOException;

}
