package com.example.demo.ORM.interfaceP;

import com.example.demo.ORM.model.Challenge;
import com.example.demo.ORM.model.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends MongoRepository<Challenge, String> {

}
