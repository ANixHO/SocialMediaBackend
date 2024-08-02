package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.CommentNotFoundException;
import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;
import org.socialmedia.model.User;
import org.socialmedia.repository.CommentRepository;
import org.socialmedia.service.CommentService;
import org.socialmedia.service.PostService;
import org.socialmedia.service.UserService;
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
    private UserService userService;
    @Autowired
    private GetPost getPost;

    @Transactional
    public Comment addComment(Long postId, Long userId, String content){
        if(content == null || content.trim().isEmpty()){
            throw new InvalidInputException("Comment content can not be empty");
        }

        User user = userService.getUserById(userId);
        Post post = getPost.getPostById(postId);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(()-> new CommentNotFoundException("Comment not found"));
    }

    public List<Comment> getCommentByPostId(Long postId){
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> getCommentByUserId(Long userId){
        return commentRepository.findByUserId(userId);
    }

    @Transactional
    public Comment updateComment(Long id, String content){
        if(content == null || content.trim().isEmpty()){
            throw new InvalidInputException("Comment content can not be empty");
        }

        Comment comment = getCommentById(id);
        comment.setContent(content);
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id){
        Comment comment = getCommentById(id);
        commentRepository.delete(comment);
    }

    public boolean isCommentOwnedByUser(Long commentId, Long userId){
        Comment comment = getCommentById(commentId);
        return comment.getUser().getId().equals(userId);
    }

    public List<Comment> getRecentCommentByPostId(Long postId, int limit){
        return commentRepository.findMostRecentByPostId(postId, PageRequest.of(0, limit));
    }


}
