package com.example.kfh.model.repository;

import com.example.kfh.model.AuthenticationLogger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthenticationLoggerRepository extends MongoRepository<AuthenticationLogger, ObjectId> {

}
