package org.socialmedia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.bson.types.Binary;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Entity
@Document(collection = "images")
public class PostImage {

    @Id
    private String id;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")
    @Transient
    private Post post;

    @Field("post_id")
    private String postId;

    private Binary image;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private int orders;


    public PostImage() {

    }

    public PostImage(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
        this.postId = post != null ? post.getId() : null;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int order) {
        this.orders = order;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
