package com.example.demo.database.mongoDB.interfaceP;

import com.example.demo.database.mongoDB.model.Credentials;
import com.example.demo.database.mongoDB.model.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    Utilisateur findUtilisateurByNom(String nom);
    Utilisateur findUtilisateurByCredentialsRef(Credentials credentials);
}
