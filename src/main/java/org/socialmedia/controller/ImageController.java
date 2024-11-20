package org.socialmedia.controller;

import org.socialmedia.dto.PostImageDTO;
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
    public ResponseEntity<PostImageDTO> getInitPostImage(@PathVariable String postId){
        try {
            PostImageDTO initPostImage = postImageService.getInitPostImageDTOByPostId(postId);
            return ResponseEntity.ok(initPostImage);

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/postDetail/{postId}")
    public ResponseEntity<List<PostImageDTO>> getPostImages(@PathVariable String postId){
        try {
            List<PostImageDTO> postImageList = postImageService.getPostImageDTOsByPostId(postId);
            return ResponseEntity.ok(postImageList);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }



}
