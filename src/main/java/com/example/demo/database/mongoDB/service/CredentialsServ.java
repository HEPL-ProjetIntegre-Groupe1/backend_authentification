package com.example.demo.database.mongoDB.service;

import com.example.demo.database.mongoDB.interfaceP.CredentialsRepository;
import com.example.demo.database.mongoDB.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServ {
    @Autowired
    private CredentialsRepository credentialsRepository;

    public Iterable<Credentials> getAllCredentials() {
        return credentialsRepository.findAll();
    }

    public Credentials getCredentialsByUsername(String username) {
        return credentialsRepository.findCredentialsByUsername(username);
    }

    public boolean insertCredentials(Credentials credentials) {
        try {
            credentialsRepository.save(credentials);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCredentials(Credentials credentials) {
        try {
            credentialsRepository.delete(credentials);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyCredentials(String username, String password) {
        var credentials = getCredentialsByUsername(username);
        return credentials != null && credentials.getPassword().equals(password);
    }

    public Credentials getCredentialsById(String _id) {
        return credentialsRepository.findCredentialsBy_id(_id);
    }
}
