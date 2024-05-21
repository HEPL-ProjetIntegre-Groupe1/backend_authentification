package Inpres.masi.backend_Authentication.ORM.service;

import Inpres.masi.backend_Authentication.ORM.interfaceP.RegistrationRepository;
import Inpres.masi.backend_Authentication.ORM.model.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class RegistrationServ {
    @Autowired
    private RegistrationRepository registrationRepository;

    public Registration getRegistrationByRegistreNational(String registreNational) {
        return registrationRepository.getRegistrationByRegistreNational(registreNational);
    }

    public Registration requestRegistration(String registreNational) {
        Registration registration = new Registration();
        registration.setRegistreNational(registreNational);

        // Inscription valable pendant 24H
        registration.setDateExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));

        return registrationRepository.save(registration);
    }

    public boolean isRegistrationValid(Registration registration) {
        return registration.getDateExpiration().after(new Date(System.currentTimeMillis()));
    }

    public void deleteRegistration(Registration registration) {
        if(registration != null) {
            registrationRepository.delete(registration);
        }
    }
}
