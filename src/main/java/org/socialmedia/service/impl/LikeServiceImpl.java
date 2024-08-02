package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.DuplicateResourceException;
import org.socialmedia.Exceptions.LikeNotFoundException;
import org.socialmedia.model.Like;
import org.socialmedia.model.Post;
import org.socialmedia.model.User;
import org.socialmedia.repository.LikeRepository;
import org.socialmedia.service.LikeService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private  LikeRepository likeRepository;
    @Autowired
    private  UserService userService;
    @Autowired
    private GetPost getPost;


    public Like addLike(Long userId, Long postId){
        User user = userService.getUserById(userId);
        Post post = getPost.getPostById(postId);

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
        Post post = getPost.getPostById(postId);
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
        Post post = getPost.getPostById(postId);
        return likeRepository.existsByUserAndPost(user,post);
    }
}
