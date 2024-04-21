package com.example.demo.database.mongoDB.interfaceP;

import com.example.demo.database.mongoDB.model.Inscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends MongoRepository<Inscription, String> {
}
