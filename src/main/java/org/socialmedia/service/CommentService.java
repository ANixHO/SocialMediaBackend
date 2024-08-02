package org.socialmedia.service;

import org.socialmedia.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Long postId, Long userId, String content);

    Comment getCommentById(Long id);

    List<Comment> getCommentByPostId(Long postId);

    List<Comment> getCommentByUserId(Long userId);

    Comment updateComment(Long id, String content);

    void deleteComment(Long id);

    boolean isCommentOwnedByUser(Long commentId, Long userId);

    List<Comment> getRecentCommentByPostId(Long postId, int limit);
}
