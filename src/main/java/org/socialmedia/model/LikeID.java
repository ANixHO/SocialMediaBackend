package org.socialmedia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LikeID implements Serializable {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long userId;

    public LikeID(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public LikeID() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeID likeID = (LikeID) o;
        return Objects.equals(postId, likeID.postId) && Objects.equals(userId, likeID.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }
}
