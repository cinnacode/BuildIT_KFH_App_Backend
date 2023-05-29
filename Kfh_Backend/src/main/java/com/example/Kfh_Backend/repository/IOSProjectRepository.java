package com.example.Kfh_Backend.repository;

import com.example.Kfh_Backend.model.IOSProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOSProjectRepository extends MongoRepository<IOSProject, Long> {
}
