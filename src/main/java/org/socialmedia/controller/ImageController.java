package org.socialmedia.controller;

import org.socialmedia.model.Post;
import org.socialmedia.model.PostImage;
import org.socialmedia.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/postImages")
public class ImageController {

    @Autowired
    private PostImageService postImageService;

    @GetMapping("/explore/{postId}")
    public ResponseEntity<PostImage> getInitPostImage(@PathVariable Long postId){
        try {
            Post post = new Post(postId);
            PostImage initPostImage = postImageService.getInitPostImage(post);
            return ResponseEntity.ok(initPostImage);

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/postDetail/{postId}")
    public ResponseEntity<List<PostImage>> getPostImages(@PathVariable Long postId){
        try {
            Post post = new Post(postId);
            List<PostImage> postImageList = postImageService.getPostImages(post);
            return ResponseEntity.ok(postImageList);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{postImageId}")
    public ResponseEntity<Void> deletePostImage(@PathVariable Long postImageId){
        try {
            PostImage postImage = new PostImage(postImageId);
            postImageService.deletePostImage(postImage);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
