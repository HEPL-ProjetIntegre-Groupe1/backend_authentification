package com.example.demo.database.mongoDB.interfaceP;

import com.example.demo.database.mongoDB.model.Credentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends MongoRepository<Credentials, String> {
    Credentials findCredentialsByUsername(String username);
    Credentials findCredentialsBy_id(String _id);
}
