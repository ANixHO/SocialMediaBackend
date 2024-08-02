package org.socialmedia.repository;

import org.socialmedia.model.Like;
import org.socialmedia.model.LikeID;
import org.socialmedia.model.Post;
import org.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeID> {
    long countByPostId(long postId);

    boolean existsByUserAndPost(User user, Post post);
    Like findByUserAndPost(User user, Post post);
}
