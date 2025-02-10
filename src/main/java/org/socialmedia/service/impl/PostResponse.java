package org.socialmedia.service.impl;

import org.socialmedia.model.*;

import java.util.List;

public class PostResponse {

    private Post post;
    private User user;
    private PostImage initPostImage;
    private List<Comment> recentComments;
    private List<PostImage> postImageList;

    public Post getPost() {
        return post;
    }

    public List<Comment> getRecentComments() {
        return recentComments;
    }

    public void setRecentComments(List<Comment> recentComments) {
        this.recentComments = recentComments;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PostImage getInitPostImage() {
        return initPostImage;
    }

    public void setInitPostImage(PostImage initPostImage) {
        this.initPostImage = initPostImage;
    }

    public List<PostImage> getPostImageList() {
        return postImageList;
    }

    public void setPostImageList(List<PostImage> postImageList) {
        this.postImageList = postImageList;
    }
}
