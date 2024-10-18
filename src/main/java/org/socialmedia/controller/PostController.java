package org.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.socialmedia.model.Post;
import org.socialmedia.service.impl.PostResponse;
import org.socialmedia.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @PostMapping(value = "/newpost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> createPost(
            @RequestPart("post") Post post,
            @RequestPart("images") List<MultipartFile> images) throws IOException {

        return ResponseEntity.ok(postService.createPost(post, images));
    }

    @GetMapping("/explore/{page}")
    public ResponseEntity<List<Post>> postsForExplorePage(@PathVariable int page) {
        return ResponseEntity.ok(postService.getPostsForExplore(page));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getSinglePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getSinglePost(new Post(postId)));
    }

    @PostMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> updatePost(@PathVariable Long postId,
                                           @RequestPart("post") Post post,
                                           @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        post.setId(postId);
        return ResponseEntity.ok(postService.updatePost(post, images));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {

        postService.deletePost(new Post(postId));
        return ResponseEntity.noContent().build();
    }



}




