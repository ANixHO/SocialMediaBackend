package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.Exceptions.PostNotFoundException;
import org.socialmedia.model.Post;
import org.socialmedia.model.PostImage;
import org.socialmedia.repository.PostRepository;
import org.socialmedia.service.CommentService;
import org.socialmedia.service.PostImageService;
import org.socialmedia.service.PostService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private static final int POST_PAGE_SIZE = 15;

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostImageService postImageService;

    @Autowired
    private UserService userService;

    @Transactional
    public Post createPost(Post post, List<MultipartFile> imageFiles) throws IOException {
        if (post.getTitle() == null || post.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Post title can not be empty");
        }
        if (post.getContentText() == null || post.getContentText().trim().isEmpty()) {
            throw new InvalidInputException("Post content can not be empty");
        }


        post.setUser(userService.getCurrUser());
        Post savedPost = postRepository.save(post);

        postImageService.saveMultiplePostImages(savedPost, imageFiles, 0);

        return savedPost;
    }

    @Transactional
    public Post updatePost(Post post, List<MultipartFile> imageFiles) throws IOException {
        userService.isOwner(post.getUser());
        Post existingPost = getPostById(post.getId());

        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }

        if (post.getContentText() != null) {
            existingPost.setContentText(post.getContentText());
        }

        existingPost.setUpdatedAt(LocalDateTime.now());

        if (!imageFiles.isEmpty()) {
            List<PostImage> list = postImageService.getPostImages(existingPost);
            int lastOrder = postImageService.getLastPostImage(post).getOrders();
            postImageService.saveMultiplePostImages(existingPost, imageFiles, lastOrder);
        }
        return postRepository.save(existingPost);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }

    @Transactional
    public void deletePost(Post post) {
        userService.isOwner(post.getUser());
        postRepository.deleteById(post.getId());
    }

    public List<Post> getPostsForExplore(int page) {
        PostResponse postResponse = new PostResponse();
        PageRequest pageRequest = PageRequest.of(page, POST_PAGE_SIZE, Sort.by("update_at").descending());
        return postRepository.findAll(pageRequest).getContent();

    }


    public Post getSinglePost(Post post){
        Optional<Post> opPost = postRepository.findById(post.getId());
        return opPost.orElseThrow(() -> new PostNotFoundException("Post Not Found"));
    }


}
