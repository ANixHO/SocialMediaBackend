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
    public ResponseEntity<Comment> addComment(@PathVariable Long postId,
                                              @RequestPart("comment") Comment comment
                                              ) {
        Post post = new Post(postId);
        return ResponseEntity.ok(commentService.addComment(post, comment));
    }

/*
    get comment
 */

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId,
                                                             int page) {
        return ResponseEntity.ok(commentService.getCommentByPost(new Post(postId), page));
    }

/*
    update comment
 */
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id,
                                                 @RequestBody Comment comment) {
        comment.setId(id);
        return ResponseEntity.ok(commentService.updateComment(comment));
    }

/*
    delete comment
 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
