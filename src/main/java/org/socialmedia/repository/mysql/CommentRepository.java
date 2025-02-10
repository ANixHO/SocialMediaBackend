package org.socialmedia.repository.mysql;

import org.socialmedia.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    Page<Comment> findByPostId(String postId, PageRequest pageRequest);
    List<Comment> findByParentCommentId(Long commentId);
    int countByPostId(String postId);

}
