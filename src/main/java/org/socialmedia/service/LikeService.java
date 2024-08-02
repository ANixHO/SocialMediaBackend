package org.socialmedia.service;

import org.socialmedia.model.Like;

public interface LikeService {
    Like addLike(Long userId, Long postId);

    void deleteLike(Long userId, Long postId);

    long getLikeCountForPost(Long postId);

    boolean hasUserLikedPost(Long userId, Long postId);

}
