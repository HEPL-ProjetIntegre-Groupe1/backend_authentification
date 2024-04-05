package com.example.demo.database.service;

import com.example.demo.database.interfaceP.utilisateurRepository;
import com.example.demo.database.model.utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class utilisateurService {
    @Autowired
    utilisateurRepository repository;

    public utilisateur getUtilisateurById(int id) {
        return repository.findById(id).get();
    }

    public utilisateur getUtilisateurByUsername(String username) {
        return repository.findByUsername(username);
    }
}
