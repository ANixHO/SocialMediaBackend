package org.socialmedia.repository;

import org.socialmedia.model.PostContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostContectRepository extends MongoRepository<PostContent, String> {
    PostContent findByPostId(Long postId);

    @Query("{'$text': {'$search': ?0}}")
    List<PostContent> fullTextSearch(String searchText);
}
