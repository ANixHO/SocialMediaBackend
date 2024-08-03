package org.socialmedia.controller;

import org.socialmedia.model.Image;
import org.socialmedia.model.Post;
import org.socialmedia.service.impl.PostResponse;
import org.socialmedia.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/allposts")
    public ResponseEntity<List<PostResponse>> getAllPostResponses() {
        return ResponseEntity.ok(postService.getAllPostResponses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostResponseById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostResponseByPostId(id));
    }

    @PostMapping("/newpost")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest newPost) {
        return ResponseEntity.ok(postService.createPost(
                newPost.getPost(),
                newPost.getImageList()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostRequest post) {
        return ResponseEntity.ok(postService.updatePost(id, post.getPost(), post.getImageList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {

        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


    //todo post views
}

class PostRequest {
    private Post post;
    private List<Image> imageList;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}


