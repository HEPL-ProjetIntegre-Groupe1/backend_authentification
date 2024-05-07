package com.example.demo.ORM.service;

import com.example.demo.ORM.interfaceP.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServ {
    @Autowired
    private RegistrationRepository registrationRepository;
}
