package org.socialmedia.controller;

import org.socialmedia.model.Comment;
import org.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

/*
    add comment
 */
    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.addComment(postId, comment));
    }

    @PostMapping("/post/{postId}/{parentCommentId}")
    public ResponseEntity<Comment> addCommentWithParentComment(@PathVariable Long postId, @PathVariable Long parentCommentId, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.addCommentWithParentComment(postId, parentCommentId, comment));
    }

/*
    get comment
 */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentByPostId(postId));
    }

/*
    update comment
 */
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(id, comment));
    }

/*
    delete comment
 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
