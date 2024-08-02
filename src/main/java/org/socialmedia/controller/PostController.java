package org.socialmedia.controller;

import org.socialmedia.model.Post;
import org.socialmedia.model.PostContent;
import org.socialmedia.service.PostResponse;
import org.socialmedia.service.PostService;
import org.socialmedia.service.UserService;
import org.socialmedia.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostResponseById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostResponseById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestBody PostContent postContent) {
        Long userId = jwtUtils.getCurrentUserId();
        post.setUser(userService.getUserById(userId));
        return ResponseEntity.ok(postService.createPost(post, postContent));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post, @RequestBody PostContent postContent){
        Long userId = jwtUtils.getCurrentUserId();
        if(!postService.isPostOwnedByUser(id,userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(postService.updatePost(id,post,postContent));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        Long userId = jwtUtils.getCurrentUserId();
        if(!postService.isPostOwnedByUser(id,userId) && !jwtUtils.isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    //todo post views
}
