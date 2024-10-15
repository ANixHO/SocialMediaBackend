package org.socialmedia.model;

import jakarta.persistence.*;
import org.bson.types.Binary;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Entity
@Document(collection = "avatars")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Binary avatar;

    @CreationTimestamp
    private LocalDateTime created;

    @OneToOne(mappedBy = "user_id")
    private User user;

    public Avatar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Binary getAvatar() {
        return avatar;
    }

    public void setAvatar(Binary avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
