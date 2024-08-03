package org.socialmedia.repository;

import org.socialmedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM Post p WHERE MATCH(p.title) AGAINST(?1 IN BOOLEAN MODE)", nativeQuery = true)
    List<Post> searchByTitle(String keywords);

    @Query(value = "SELECT p FROM Post p WHERE MATCH(p.contentText) AGAINST(?1 IN BOOLEAN MODE)", nativeQuery = true)
    List<Post> searchByContentText(String keywords);
}
