package org.socialmedia.controller;

import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;
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
    @PostMapping("/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable String postId,
                                              @RequestPart("comment") Comment comment
                                              ) {
        Post post = new Post(postId);
        return ResponseEntity.ok(commentService.addComment(post, comment));
    }

/*
    get comment
 */

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable String postId,
                                                             int page) {
        return ResponseEntity.ok(commentService.getCommentByPost(new Post(postId), page));
    }

/*
    update comment
 */
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable String commentId,
                                                 @RequestBody Comment comment) {
        comment.setId(commentId);
        return ResponseEntity.ok(commentService.updateComment(comment));
    }

/*
    delete comment
 */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
