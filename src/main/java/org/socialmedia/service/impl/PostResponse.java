package org.socialmedia.service.impl;

import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;

import java.util.List;

public class PostResponse {

    private Post post;
    private List<Comment> recentComments;
    private List<Long> imageIdList;

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

    public List<Long> getImageIdList() {
        return imageIdList;
    }

    public void setImageIdList(List<Long> imageIdList) {
        this.imageIdList = imageIdList;
    }
}
