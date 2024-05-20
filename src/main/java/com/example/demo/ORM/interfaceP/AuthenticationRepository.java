package com.example.demo.ORM.interfaceP;

import com.example.demo.ORM.model.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenticationRepository extends MongoRepository<Authentication, String> {
    List<Authentication> getAuthenticationsByRegistreNationalAndDeviceConnexionEquals(String registreNational, String deviceConnexion);
    List<Authentication> getAuthenticationsByRegistreNationalAndOnGoingEquals(String registreNational, boolean onGoing);
}
