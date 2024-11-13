package org.socialmedia.service;

import org.socialmedia.dto.PostDTO;
import org.socialmedia.model.Post;
import org.socialmedia.model.User;
import org.socialmedia.service.impl.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostDTO createPost(Post post, List<MultipartFile> imageFiles) throws IOException;
    PostDTO updatePost(PostDTO post, List<MultipartFile> imageFiles) throws IOException;
    Post getPostById(String id);
    PostDTO getPostDTOById(String id);
    PostDTO getSinglePost(String id);
    List<PostDTO> getPostsForExplore(int page);
    void deletePost(String postId);
    void deletePostImage(String postId, List<String> postImageIdList);
}
