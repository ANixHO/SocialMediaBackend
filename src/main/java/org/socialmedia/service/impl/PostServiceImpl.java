package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.InvalidInputException;
import org.socialmedia.Exceptions.PostNotFoundException;
import org.socialmedia.model.Post;
import org.socialmedia.model.PostContent;
import org.socialmedia.repository.PostContectRepository;
import org.socialmedia.repository.PostRepository;
import org.socialmedia.service.CommentService;
import org.socialmedia.service.LikeService;
import org.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostContectRepository postContentRepository;

    @Transactional
    public Post createPost(Post post, PostContent postContent){
        if(post.getTitle() == null || post.getTitle().trim().isEmpty()){
            throw new InvalidInputException("Post title can not be empty");
        }

        post.setCreatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post);

        postContent.setPostId(savedPost.getId());
        postContent.setLastEditedAt(savedPost.getCreatedAt());
        PostContent savedPostContent = postContentRepository.save(postContent);

        savedPost.setMongodbContentID(savedPostContent.getId());
        return postRepository.save(savedPost);
    }

    public Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(()-> new PostNotFoundException("Post not found with id: " + id));
    }

    public PostResponse getPostResponseById(Long id){
        PostResponse postResponse = new PostResponse(getPostById(id));
        PostContent existingPostContent = postContentRepository.findByPostId(id);

        postResponse.setLikeCount(likeService.getLikeCountForPost(id));
        postResponse.setRecentComments(commentService.getRecentCommentByPostId(id,5));
        postResponse.setPostContent(existingPostContent);
        existingPostContent.setViewCount(existingPostContent.getViewCount() + 1);
        postContentRepository.save(existingPostContent);

        return postResponse;
    }


    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Transactional
    public Post updatePost(Long id, Post post, PostContent postContent){
        Post existingPost = getPostById(id);

        if(post.getTitle() == null){
            post.setTitle(existingPost.getTitle());
        }

        existingPost.setUpdatedAt(LocalDateTime.now());
        Post updatedPost = postRepository.save(post);

        PostContent existingPostContent = postContentRepository.findByPostId(id);

        if(postContent.getFullContent() != null){
            existingPostContent.setFullContent(postContent.getFullContent());
        }
        existingPostContent.setLastEditedAt(existingPost.getUpdatedAt());
        postContentRepository.save(existingPostContent);
        return updatedPost;
    }

    @Transactional
    public void deletePost(Long id){
        Post post = getPostById(id);
        postContentRepository.deleteById(post.getMongodbContentID());
        postRepository.deleteById(id);
    }

    public boolean isPostOwnedByUser(Long postId, Long userId){
        Post post = getPostById(postId);
        return post.getUser().getId().equals(userId);

    }
}
