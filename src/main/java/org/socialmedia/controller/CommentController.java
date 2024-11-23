package org.socialmedia.controller;

import org.socialmedia.dto.CommentDTO;
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
    public ResponseEntity<CommentDTO> addComment(@PathVariable String postId,
                                                 @RequestPart("comment") String content
                                              ) {
        return ResponseEntity.ok(commentService.addComment(postId, content));
    }

/*
    get comment
 */

    @GetMapping("/{postId}/{page}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable String postId,
                                                             @PathVariable int page) {
        return ResponseEntity.ok(commentService.getCommentDTOByPostId(postId, page));
    }

/*
    update comment
 */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.updateComment(commentDTO));
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
