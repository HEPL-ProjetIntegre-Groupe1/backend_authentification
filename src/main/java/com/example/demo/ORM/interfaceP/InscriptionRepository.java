package com.example.demo.ORM.interfaceP;

import com.example.demo.ORM.model.Inscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends MongoRepository<Inscription, String> {
}
