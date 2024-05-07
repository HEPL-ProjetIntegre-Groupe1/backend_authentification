package com.example.demo.ORM.interfaceP;

import com.example.demo.ORM.model.Authentication;
import com.example.demo.ORM.model.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends MongoRepository<Authentication, String> {
}
