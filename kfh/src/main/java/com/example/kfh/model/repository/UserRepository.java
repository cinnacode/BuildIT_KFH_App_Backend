package com.example.kfh.model.repository;

import org.bson.types.ObjectId;
import com.example.kfh.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
@Repository
public interface UserRepository {
    Optional<User> findByUsername(String username);

    @Query(value = "{slug: ?0}", fields = "{ 'slug': 0}")
    public Optional<User> findBySlug(String slug);

}
