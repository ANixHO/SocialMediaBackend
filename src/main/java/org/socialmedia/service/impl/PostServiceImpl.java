package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.Exceptions.PostNotFoundException;
import org.socialmedia.dto.PostDTO;
import org.socialmedia.model.Post;
import org.socialmedia.model.PostImage;
import org.socialmedia.repository.mongodb.PostImageRepository;
import org.socialmedia.repository.mysql.PostRepository;
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
import java.util.ArrayList;
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
    @Autowired
    private PostImageRepository postImageRepository;

    @Transactional
    public PostDTO createPost(Post post, List<MultipartFile> imageFiles) throws IOException {

        post.setTitle(titleValidate(post.getTitle()));
        post.setContentText(titleValidate(post.getContentText()));
        post.setUser(userService.getCurrUser());
        Post savedPost = postRepository.save(post);

        postImageService.saveMultiplePostImages(savedPost, imageFiles, 0);

        return convertToDTO(savedPost);
    }

    @Transactional
    public PostDTO updatePost(PostDTO postDTO, List<MultipartFile> imageFiles) throws IOException {
        userService.isOwner(postDTO.getUserId());
        Post existingPost = getPostById(postDTO.getId());

        if (postDTO.getTitle() != null) {
            existingPost.setTitle(titleValidate(postDTO.getTitle()));
        }

        if (postDTO.getContentText() != null) {
            existingPost.setContentText(contentTextValidate(postDTO.getContentText()));
        }

        existingPost.setUpdatedAt(LocalDateTime.now());

        if (!imageFiles.isEmpty()) {
            int lastOrder = postImageService.getLastPostImage(postDTO.getId()).getOrders();
            postImageService.saveMultiplePostImages(postDTO.getId(), imageFiles, lastOrder);
        }
        existingPost = postRepository.save(existingPost);
        return convertToDTO(existingPost);
    }

    public Post getPostById(String id){
       return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }

    public PostDTO getPostDTOById(String id) {
         return convertToDTO(getPostById(id));
    }

    @Transactional
    public void deletePost(String postId) {
        PostDTO postDTO = getPostDTOById(postId);

        userService.isOwner(postDTO.getUserId());
        postRepository.deleteById(postDTO.getId());
    }

    @Transactional
    public void deletePostImage(String postId, List<String> postImageIdList){
        PostDTO postDTO = getPostDTOById(postId);
        userService.isOwner(postDTO.getUserId());

        for (String postImageId : postImageIdList){
            PostImage image = postImageRepository.findById(postImageId).orElseThrow(
                    ()->new PostNotFoundException("Post image id: " + postImageId + " of post id: " + postId + " not found")
            );
            if (image.getPostId().equals(postId)) {
                postImageService.deletePostImage(postImageId);
            } else {
                throw new PostNotFoundException("Post image id: " + postImageId + " is not belong to post id: " + postId );
            }
        }
    }

    public List<PostDTO> getPostsForExplore(int page) {
        PageRequest pageRequest = PageRequest.of(page, POST_PAGE_SIZE, Sort.by("update_at").descending());
        List<Post> posts = postRepository.findAll(pageRequest).getContent();
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post : posts){
            postDTOS.add(convertToDTO(post));
        }
        return postDTOS;
    }


    public PostDTO getSinglePost(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));
        return convertToDTO(post);
    }

    private PostDTO convertToDTO(Post post){
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContentText(post.getContentText());
        dto.setDateTime(post.getUpdatedAt());
        dto.setUserId(post.getUser().getId());

        return dto;
    }

    private String titleValidate(String title){
        if (title.isEmpty() || title.trim().isEmpty()) {
            throw new InvalidInputException("Post title can not be empty");
        }
        return title;
    }

    private String contentTextValidate(String contentText){
        if (contentText == null || contentText.trim().isEmpty()) {
            throw new InvalidInputException("Post content can not be empty");
        }
        return contentText;
    }
}
