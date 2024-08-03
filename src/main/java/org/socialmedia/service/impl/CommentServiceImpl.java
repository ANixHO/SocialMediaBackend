package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.CommentNotFoundException;
import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;
import org.socialmedia.repository.CommentRepository;
import org.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GetPost getPost;

/*
    add comment
 */
    @Transactional
    public Comment addComment(Long postId, Comment comment) {
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new InvalidInputException("Comment content can not be empty");
        }

        comment.setPostId(postId);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment addCommentWithParentComment(Long postId, Long parentCommentId, Comment comment) {
        comment.setParentCommentId(parentCommentId);
        return commentRepository.save(comment);
    }

/*
    get comment
*/

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    public List<Comment> getCommentByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> getRecentCommentByPostId(Long postId, int limit) {
        return commentRepository.findMostRecentByPostId(postId, PageRequest.of(0, limit));
    }

/*
    update comment
 */

    @Transactional
    public Comment updateComment(Long id, Comment comment) {
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new InvalidInputException("Comment content can not be empty");
        }

        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

/*
    delete comment
 */
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = getCommentById(id);
        List<Comment> commentList = commentRepository.findByParentCommentId(id);
        if(!commentList.isEmpty()) {
            comment.setContent(null);
            comment.setUpdatedAt(LocalDateTime.now());
            commentRepository.save(comment);
        }else {
            commentRepository.delete(comment);
        }
    }




}
