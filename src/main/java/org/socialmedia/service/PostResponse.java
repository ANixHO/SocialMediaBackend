package org.socialmedia.service;

import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;

import java.util.List;

public class PostResponse {

    private Post post;
    private long likeCount;
    private List<Comment> recentComments;

    public PostResponse(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public List<Comment> getRecentComments() {
        return recentComments;
    }

    public void setRecentComments(List<Comment> recentComments) {
        this.recentComments = recentComments;
    }
}
