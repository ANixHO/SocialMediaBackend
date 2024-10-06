package org.socialmedia.service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    byte[] getImageDataById(Long imageId) throws IOException;

    List<Long> getImageUrlsByPostId(Long postId);

    void deleteImageById(Long imageId);

    void deleteImageByPostId(Long postId);

    void saveImage(Long postId, List<MultipartFile> imageFiles);

}
