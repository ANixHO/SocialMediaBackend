package org.socialmedia.repository;

import org.socialmedia.model.PostImage;
import org.socialmedia.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends MongoRepository<PostImage, Long> {
    List<PostImage> findByPost(Post post);

    PostImage findFirstByPostOrderByOrdersAsc(Post post);
    PostImage findFirstByPostOrderByOrdersDesc(Post post);
}
