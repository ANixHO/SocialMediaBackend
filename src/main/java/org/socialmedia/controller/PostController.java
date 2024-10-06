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
            @RequestPart("post") String postJson,
            @RequestPart("images") List<MultipartFile> images) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(postJson, Post.class);
        return ResponseEntity.ok(postService.createPost(post, images));
    }

    @GetMapping("/explore")
    public ResponseEntity<List<PostResponse>> getAllPostResponses() {
        return ResponseEntity.ok(postService.getAllPostResponses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostResponseById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostResponseByPostId(id));
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestPart("post") String postJson,
                                           @RequestPart(value = "images", required = false) Optional<List<MultipartFile>> images) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(postJson, Post.class);

        List<MultipartFile> imageFiles = images.orElse(Collections.emptyList());

        return ResponseEntity.ok(postService.updatePost(id, post, imageFiles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {

        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<Boolean> likePost(@PathVariable Long postId){
        return ResponseEntity.ok(postService.likeFunction(postId));
    }


}




