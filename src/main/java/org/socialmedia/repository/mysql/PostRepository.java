
package org.socialmedia.repository.mysql;

import org.socialmedia.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Optional<Post> findById(String id);
    Page<Post> findAll(Pageable pageable);

}
