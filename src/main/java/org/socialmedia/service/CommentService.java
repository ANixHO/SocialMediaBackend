package org.socialmedia.service;

import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;
import org.socialmedia.model.User;

import java.util.List;

public interface CommentService {
    Comment addComment(Post post, Comment comment, User user);

    Comment getComment(Long id);

    List<Comment> getCommentByPost(Post post);

    Comment updateComment(Comment comment);

    void deleteComment(Long id);
}
