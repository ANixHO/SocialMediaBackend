package org.socialmedia.service;

import org.socialmedia.model.Image;
import org.socialmedia.model.Post;
import org.socialmedia.service.impl.PostResponse;

import java.util.List;

public interface PostService {
    Post createPost(Post post, List<Image> imageList);

    Post getPostById(Long id);

    PostResponse getPostResponseByPostId(Long id);

    List<PostResponse> getAllPostResponses();

    Post updatePost(Long id, Post post, List<Image> imageList);

    void deletePost(Long id);

}
