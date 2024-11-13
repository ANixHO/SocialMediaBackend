package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.CommentException;
import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.Exceptions.UserException;
import org.socialmedia.dto.CommentDTO;
import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;
import org.socialmedia.model.User;
import org.socialmedia.repository.mysql.CommentRepository;
import org.socialmedia.service.CommentService;
import org.socialmedia.service.PostService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private static final int COMMENT_PAGE_SIZE = 15;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    /*
        add comment
     */
    @Transactional
    public CommentDTO addComment(String postId, String content) {
        Comment comment = new Comment();
        comment.setPost(postService.getPostById(postId));
        comment.setUser(userService.getCurrUser());
        comment.setContent(validComment(content));
        comment = commentRepository.save(comment);

        return convertToDTO(comment);
    }

/*
    get comment
*/

    public Comment getComment(String commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("Comment not found"));
    }

    public CommentDTO getCommentDTO(String commentId) {
        return convertToDTO(getComment(commentId));
    }

    public List<CommentDTO> getCommentDTOByPostId(String postId, int page) {
        PageRequest pageRequest = PageRequest.of(page, COMMENT_PAGE_SIZE, Sort.by("updated_at").descending());
        Page<Comment> commentPage = commentRepository.findByPostId(postId, pageRequest);
        List<Comment> list = commentPage.getContent();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : list) {
            commentDTOS.add(convertToDTO(comment));
        }

        return commentDTOS;
    }

/*
    update comment
 */

    @Transactional
    public CommentDTO updateComment(CommentDTO commentDTO) {
        Comment oldComment = getComment(commentDTO.getId());

        userService.isOwner(oldComment.getUser().getId());

        oldComment.setContent(validComment(commentDTO.getContent()));
        oldComment = commentRepository.save(oldComment);
        return convertToDTO(oldComment);
    }

    /*
        delete comment
     */
    @Transactional
    public void deleteComment(String commentId) {
        userService.isOwner(getComment(commentId).getUser().getId());
        commentRepository.deleteById(commentId);

    }

    private String validComment(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new InvalidInputException("Comment content can not be empty");
        }
        return content;
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPost().getId());
        dto.setContent(comment.getContent());
        dto.setDateTime(comment.getUpdatedAt());
        dto.setUserId(comment.getUser().getId());

        return dto;
    }


}
