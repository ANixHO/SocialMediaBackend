package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.Exceptions.PostNotFoundException;
import org.socialmedia.model.Post;
import org.socialmedia.repository.PostRepository;
import org.socialmedia.service.CommentService;
import org.socialmedia.service.ImageService;
import org.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImageService imageService;


    @Transactional
    public Post createPost(Post post, List<MultipartFile> imageFiles) throws IOException {
        if (post.getTitle() == null || post.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Post title can not be empty");
        }
        if (post.getContentText() == null || post.getContentText().trim().isEmpty()) {
            throw new InvalidInputException("Post content can not be empty");
        }


        post.setCreatedAt(LocalDateTime.now());
        post.setLikePost(false);
        Post savedPost = postRepository.save(post);
        Long savedPostId = savedPost.getId();

        imageService.saveImage(savedPostId, imageFiles);

        return savedPost;
    }

    @Transactional
    public Post updatePost(Long id, Post post, List<MultipartFile> imageFiles) {
        Post existingPost = getPostById(id);

        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }

        if (post.getContentText() != null) {
            existingPost.setContentText(post.getContentText());
        }

        existingPost.setUpdatedAt(LocalDateTime.now());
        if (!imageFiles.isEmpty()) {
            imageService.saveImage(id, imageFiles);
        }

        return postRepository.save(existingPost);
    }

    public PostResponse getPostResponseByPostId(Long id) {
        PostResponse postResponse = new PostResponse(getPostById(id));
        postResponse.setRecentComments(commentService.getRecentCommentByPostId(id, 5));
        postResponse.setImageIdList(imageService.getImageUrlsByPostId(id));

        return postResponse;
    }


    public List<PostResponse> getAllPostResponses() {
        List<Post> postList = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : postList) {
            postResponses.add(getPostResponseByPostId(post.getId()));
        }

        return postResponses;
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }


    @Transactional
    public void deletePost(Long id) {
        imageService.deleteImageByPostId(id);
        postRepository.deleteById(id);
    }

    public boolean likeFunction(Long postId) {
        Post post = getPostById(postId);
        if (post.isLikePost()) {
            post.setLikePost(false);
        } else {
            post.setLikePost(true);
        }
        postRepository.save(post);

        return post.isLikePost();
    }


}
