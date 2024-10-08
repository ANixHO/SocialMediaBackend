package org.socialmedia.repository;

import org.socialmedia.model.Image;
import org.socialmedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends MongoRepository<Image, Long> {
    List<Image> findByPost(Post post);

}
