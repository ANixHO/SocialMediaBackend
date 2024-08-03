package org.socialmedia.repository;

import org.socialmedia.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> searchImageByPostId(Long postId);
    void deleteImageByPostId(Long postId);
}
