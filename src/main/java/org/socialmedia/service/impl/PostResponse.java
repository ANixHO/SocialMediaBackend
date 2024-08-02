package org.socialmedia.service.impl;

import org.socialmedia.model.Comment;
import org.socialmedia.model.Post;
import org.socialmedia.model.PostContent;

import java.util.List;

public class PostResponse {

    private Post post;
    private long likeCount;
    private List<Comment> recentComments;
    private PostContent postContent;

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

    public PostContent getPostContent() {
        return postContent;
    }

    public void setPostContent(PostContent postContent) {
        this.postContent = postContent;
    }
}
