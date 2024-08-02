package org.socialmedia.controller;

import org.socialmedia.model.Like;
import org.socialmedia.service.LikeService;
import org.socialmedia.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeContorller {

    @Autowired
    private LikeService likeService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/post/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Like> addLike(@PathVariable Long postId){
        Long userId = jwtUtils.getCurrentUserId();
        return ResponseEntity.ok(likeService.addLike(userId,postId));
    }

    @DeleteMapping("/post/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteLike(@PathVariable Long postId){
        Long userId = jwtUtils.getCurrentUserId();
        likeService.deleteLike(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("post/{postId}/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId){
        return ResponseEntity.ok(likeService.getLikeCountForPost(postId));
    }

    @GetMapping("/post/{postId}/status")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> getUserLikeStatus(@PathVariable Long postId){
        Long userId = jwtUtils.getCurrentUserId();
        return ResponseEntity.ok(likeService.hasUserLikedPost(userId,postId));
    }
}
