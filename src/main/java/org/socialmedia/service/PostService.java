package org.socialmedia.service;

import org.socialmedia.model.Post;
import org.socialmedia.model.PostContent;
import org.socialmedia.service.impl.PostResponse;

import java.util.List;

public interface PostService {
    Post createPost(Post post, PostContent postContent);

    Post getPostById(Long id);

    PostResponse getPostResponseById(Long id);

    List<Post> getAllPosts();

    Post updatePost(Long id, Post post, PostContent postContent);

    void deletePost(Long id);

    boolean isPostOwnedByUser(Long postId, Long userId);
}
