package org.socialmedia.config;

import org.socialmedia.model.Post;
import org.socialmedia.repository.mongodb.AvatarRepository;
import org.socialmedia.repository.mongodb.PostImageRepository;
import org.socialmedia.repository.mysql.CommentRepository;
import org.socialmedia.repository.mysql.PostRepository;
import org.socialmedia.service.AvatarService;
import org.socialmedia.service.CommentService;
import org.socialmedia.service.PostImageService;
import org.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("OwnershipSecurity")
public class OwnershipSecurity {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostImageRepository postImageRepository;
    @Autowired
    private AvatarRepository avatarRepository;

    public boolean isPostOwner(Authentication auth, String postId){
        Post existPost = postRepository.findById(postId).orElse(null);

        if (existPost == null) return false;

        return existPost.getUser().getUsername().equals(auth.getName());
    }
}
