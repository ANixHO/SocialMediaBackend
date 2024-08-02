package org.socialmedia.service;

import org.socialmedia.Exceptions.DuplicateResourceException;
import org.socialmedia.Exceptions.LikeNotFoundException;
import org.socialmedia.model.Like;
import org.socialmedia.model.Post;
import org.socialmedia.model.User;
import org.socialmedia.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class  LikeService {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    public LikeService(LikeRepository likeRepository, PostService postService, UserService userService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Like addLike(Long userId, Long postId){
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);

        if(likeRepository.existsByUserAndPost(user, post)){
            throw new DuplicateResourceException("User has already liked this post");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setCreatedAt(LocalDateTime.now());

        return likeRepository.save(like);
    }

    @Transactional
    public void deleteLike(Long userId, Long postId){
        Post post = postService.getPostById(postId);
        User user = userService.getUserById(userId);

        if(!likeRepository.existsByUserAndPost(user, post)){
            throw new LikeNotFoundException("Like not found for user and post");
        }

        Like like = likeRepository.findByUserAndPost(user, post);
        likeRepository.delete(like);
    }

    public long getLikeCountForPost(Long postId){
        return likeRepository.countByPostId(postId);
    }

    public boolean hasUserLikedPost(Long userId, Long postId){
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);
        return likeRepository.existsByUserAndPost(user,post);
    }
}
