package org.socialmedia.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts", indexes = {
        @Index(name = "index_title_fulltext", columnList = "title", unique = false),
        @Index(name = "index_content_text", columnList = "contentText", unique = false)
})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(
            columnDefinition = "TEXT",
            nullable = false
    )
    private String contentText;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Comment> comments;


    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    private List<PostImage> postImages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<PostImage> postImages) {
        this.postImages = postImages;
    }
}
