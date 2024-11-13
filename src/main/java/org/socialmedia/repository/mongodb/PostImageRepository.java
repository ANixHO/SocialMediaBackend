package org.socialmedia.repository.mongodb;

import org.socialmedia.model.PostImage;
import org.socialmedia.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends MongoRepository<PostImage, String> {
    List<PostImage> findByPost(String postId);

    PostImage findFirstByPostIdOrderByOrdersAsc(String postId);
    PostImage findFirstByPostIdOrderByOrdersDesc(String postId);
}
