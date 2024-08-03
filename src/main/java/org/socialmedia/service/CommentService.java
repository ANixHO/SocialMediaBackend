package org.socialmedia.service;

import org.socialmedia.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Long postId, Comment comment);

    Comment addCommentWithParentComment(Long postId, Long parentCommentId, Comment comment);

    Comment getCommentById(Long id);

    List<Comment> getRecentCommentByPostId(Long postId, int limit);

    List<Comment> getCommentByPostId(Long postId);

    Comment updateComment(Long id, Comment comment);

    void deleteComment(Long id);
}
