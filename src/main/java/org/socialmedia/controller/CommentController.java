package org.socialmedia.controller;

import org.socialmedia.model.Comment;
import org.socialmedia.service.CommentService;
import org.socialmedia.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentByPostId(postId));
    }

    @PostMapping("/post/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody String content) {
        Long userId = jwtUtils.getCurrentUserId();
        return ResponseEntity.ok(commentService.addComment(postId, userId, content));
    }


    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody String content) {
        Long userId = jwtUtils.getCurrentUserId();
        if (!commentService.isCommentOwnedByUser(id, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(commentService.updateComment(id, content));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id) {
        Long userId = jwtUtils.getCurrentUserId();
        if (!commentService.isCommentOwnedByUser(id, userId) && !jwtUtils.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
