package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.Exceptions.PostNotFoundException;
import org.socialmedia.model.Image;
import org.socialmedia.model.Post;
import org.socialmedia.repository.ImageRepository;
import org.socialmedia.repository.PostRepository;
import org.socialmedia.service.CommentService;
import org.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ImageRepository imageRepository;

    @Transactional
    public Post createPost(Post post, List<Image> imageList) {
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


        for (Image image : imageList) {
            image.setPostId(savedPostId);
            image.setStatus("exist");
            image.setCreatedAt(LocalDateTime.now());
            imageRepository.save(image);
        }

        return savedPost;
    }

    public PostResponse getPostResponseByPostId(Long id) {
        PostResponse postResponse = new PostResponse(getPostById(id));
        postResponse.setRecentComments(commentService.getRecentCommentByPostId(id, 5));
        postResponse.setImageList(imageRepository.searchImageByPostId(id));

        return postResponse;
    }


    public List<PostResponse> getAllPostResponses() {
        List<Post> postList = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();

        for(Post post : postList){
            postResponses.add(getPostResponseByPostId(post.getId()));
        }

        return postResponses;
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }

    @Transactional
    public Post updatePost(Long id, Post post, List<Image> imageList) {
        Post existingPost = getPostById(id);

        if (post.getTitle() == null) {
            post.setTitle(existingPost.getTitle());
        }

        for (Image image : imageList){
            if(image.getStatus().equals("delete")){
                imageRepository.deleteById(image.getId());
            } else if (image.getStatus().equals("exist")) {
                continue;
            }else if (image.getStatus().equals("new")){
                image.setPostId(id);
                image.setCreatedAt(LocalDateTime.now());
                imageRepository.save(image);
            }
        }

        existingPost.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = getPostById(id);
        imageRepository.deleteImageByPostId(id);
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
