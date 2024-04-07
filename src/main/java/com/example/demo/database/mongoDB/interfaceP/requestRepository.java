package com.example.demo.database.mongoDB.interfaceP;

import com.example.demo.database.mongoDB.model.request;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface requestRepository extends MongoRepository<request, String> {
}
