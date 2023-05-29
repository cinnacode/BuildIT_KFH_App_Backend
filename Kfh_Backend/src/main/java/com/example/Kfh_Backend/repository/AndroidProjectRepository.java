package com.example.Kfh_Backend.repository;

import com.example.Kfh_Backend.model.AndroidProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AndroidProjectRepository extends MongoRepository<AndroidProject, Long> {
}

