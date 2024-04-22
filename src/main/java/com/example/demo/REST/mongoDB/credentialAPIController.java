package com.example.demo.REST.mongoDB;

import com.example.demo.database.mongoDB.model.Credentials;
import com.example.demo.database.mongoDB.service.CredentialsServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mongoDB/credential")
public class credentialAPIController {
    @Autowired
    private CredentialsServ credentialsServ;

    @GetMapping
    public Iterable<Credentials> getAllCredentials() {
        return credentialsServ.getAllCredentials();
    }

    @GetMapping("/byId")
    public Credentials getCredentialsById(@RequestParam(name = "credentialsId") String credentialsId) {
        return credentialsServ.getCredentialsById(credentialsId);
    }

    @GetMapping("/byUsername")
    public Credentials getCredentialsByUsername(@RequestParam(name = "username") String username) {
        return credentialsServ.getCredentialsByUsername(username);
    }

    @PostMapping
    public boolean addCredentials(@RequestBody Credentials credentials) {
        return credentialsServ.insertCredentials(credentials);
    }

    @DeleteMapping
    public boolean deleteCredentials(@RequestParam(name = "credentialsId") String credentialsId) {
        Credentials credentials = credentialsServ.getCredentialsById(credentialsId);
        if(credentials != null) {
            return credentialsServ.deleteCredentials(credentials);
        } else {
            return false;
        }
    }
}
