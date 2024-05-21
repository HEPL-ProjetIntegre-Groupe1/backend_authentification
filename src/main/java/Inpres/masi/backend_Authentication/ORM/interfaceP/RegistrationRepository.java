package Inpres.masi.backend_Authentication.ORM.interfaceP;

import Inpres.masi.backend_Authentication.ORM.model.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends MongoRepository<Registration, String> {
    Registration getRegistrationByRegistreNational(String registreNational);
}
