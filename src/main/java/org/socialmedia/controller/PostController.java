package org.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.socialmedia.dto.PostDTO;
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
    public ResponseEntity<PostDTO> createPost(
            @RequestPart("post") String postJson,
            @RequestPart("images") List<MultipartFile> images) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Post post = mapper.readValue(postJson, Post.class);
        return ResponseEntity.ok(postService.createPost(post, images));
    }

    @GetMapping("/explore/{page}")
    public ResponseEntity<List<PostDTO>> postsForExplorePage(@PathVariable int page) {
        return ResponseEntity.ok(postService.getPostsForExplore(page));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable String postId) {
        return ResponseEntity.ok(postService.getSinglePost(postId));
    }

    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> updatePost(@PathVariable String postId,
                                           @RequestPart("post") String postJson,
                                           @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PostDTO postDTO = mapper.readValue(postJson, PostDTO.class);
        postDTO.setId(postId);
        return ResponseEntity.ok(postService.updatePost(postDTO, images));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {

        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}/postImages")
    public ResponseEntity<Void> deletePostImages(@PathVariable String postId,
                                                 @RequestBody List<String> postImageIdList){
        postService.deletePostImage(postId, postImageIdList);
        return ResponseEntity.noContent().build();
    }



}




