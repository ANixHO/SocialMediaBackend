package org.socialmedia.service;

import org.socialmedia.dto.CommentDTO;
import org.socialmedia.model.Comment;

import java.util.List;

public interface CommentService {
    CommentDTO addComment(String postId, String content);

    Comment getComment(String id);

    CommentDTO getCommentDTO(String commentId);

    List<CommentDTO> getCommentDTOByPostId(String postId, int page);

    CommentDTO updateComment(CommentDTO commentDTO);

    void deleteComment(String commentId);
}
