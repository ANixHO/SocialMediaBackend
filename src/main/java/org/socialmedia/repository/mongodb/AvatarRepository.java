package org.socialmedia.repository.mongodb;

import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar,String> {

    Avatar findByUserId(String userId);

    void deleteByUserId(String userId);

}
