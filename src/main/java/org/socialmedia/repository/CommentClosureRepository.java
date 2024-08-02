package org.socialmedia.repository;

import org.socialmedia.model.CommentClosure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentClosureRepository extends JpaRepository<CommentClosure, Long> {
    List<CommentClosure> findByAncestorIdOrderByDepthAsc(Long ancestorId);
    List<CommentClosure> findByDescendentIdOrderByDepthDesc(Long descendentId);
}
