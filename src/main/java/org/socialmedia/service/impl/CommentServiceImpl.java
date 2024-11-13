package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.CommentException;
import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.Exceptions.UserException;
import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;
import org.socialmedia.repository.mysql.CommentRepository;
import org.socialmedia.service.CommentService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    /*
        add comment
     */
    @Transactional
    public Comment addComment(Post post, Comment comment) {
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new InvalidInputException("Comment content can not be empty");
        }

        comment.setPost(post);
        comment.setUser(userService.getCurrUser());

        return commentRepository.save(comment);
    }

/*
    get comment
*/

    public Comment getComment(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentException("Comment not found"));
    }

    public List<Comment> getCommentByPost(Post post, int page) {
        PageRequest pageRequest = PageRequest.of(page, COMMENT_PAGE_SIZE, Sort.by("updated_at").descending());
        Page<Comment> commentPage = commentRepository.findByPostId(post.getId(), pageRequest);
        return commentPage.getContent();
    }

/*
    update comment
 */

    @Transactional
    public Comment updateComment(Comment comment) {
        Comment oldComment = getComment(comment.getId());

        if (userService.isOwner(comment.getUser())) {
            throw new UserException("Invalid User");
        }

        if (!comment.getId().equals(oldComment.getId())) {
            throw new CommentException("Invalid comment update request");
        }
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new CommentException("Comment content can not be empty");
        }

        oldComment.setContent(comment.getContent());
        oldComment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(oldComment);
    }

    /*
        delete comment
     */
    @Transactional
    public void deleteComment(String id) {
        userService.isOwner(getComment(id).getUser());
        commentRepository.deleteById(id);

    }


}
