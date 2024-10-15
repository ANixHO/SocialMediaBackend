package org.socialmedia.repository;

import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar, Long> {

    Avatar findByUser(User user);

    void deleteByUser(User user);
}
