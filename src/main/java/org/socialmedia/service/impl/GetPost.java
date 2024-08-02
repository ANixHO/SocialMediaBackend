package org.socialmedia.service.impl;

import org.socialmedia.Exceptions.PostNotFoundException;
import org.socialmedia.model.Post;
import org.socialmedia.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component
public class GetPost {

    private PostRepository postRepository;

    public Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(()-> new PostNotFoundException("Post not found with id: " + id));
    }
}
