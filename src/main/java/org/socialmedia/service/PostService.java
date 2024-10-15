package org.socialmedia.service;

import org.socialmedia.model.Post;
import org.socialmedia.model.User;
import org.socialmedia.service.impl.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Post createPost(Post post, List<MultipartFile> imageFiles) throws IOException;
    Post updatePost(Post post, List<MultipartFile> imageFiles) throws IOException;
    Post getPostById(Long id);
    Post getSinglePost(Post post);
    List<Post> getPostsForExplore(int page);
    void deletePost(Post post);
}
