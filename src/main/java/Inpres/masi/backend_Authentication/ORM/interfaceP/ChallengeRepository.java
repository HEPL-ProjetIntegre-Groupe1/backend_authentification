package Inpres.masi.backend_Authentication.ORM.interfaceP;

import Inpres.masi.backend_Authentication.ORM.model.Challenge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends MongoRepository<Challenge, String> {

}
