package org.socialmedia.service.impl;

import org.socialmedia.model.Comment;
import org.socialmedia.model.Image;
import org.socialmedia.model.Post;

import java.util.List;

public class PostResponse {

    private Post post;
    private List<Image> imageList;
    private List<Comment> recentComments;

    public PostResponse(Post post) {
        this.post = post;
    }

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

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
