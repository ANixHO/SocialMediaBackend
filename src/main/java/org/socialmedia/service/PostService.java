package org.socialmedia.service;

import org.socialmedia.model.Post;
import org.socialmedia.service.impl.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Post createPost(Post post, List<MultipartFile> imageFiles) throws IOException;

    Post getPostById(Long id);

    PostResponse getPostResponseByPostId(Long id);

    List<PostResponse> getAllPostResponses();

    Post updatePost(Long id, Post post,List<MultipartFile> imageFiles);

    void deletePost(Long id);

}
