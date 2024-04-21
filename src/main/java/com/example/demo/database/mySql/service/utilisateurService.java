package com.example.demo.database.mySql.service;

import com.example.demo.database.mySql.interfaceP.utilisateurMysqlRepository;
import com.example.demo.database.mySql.model.utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class utilisateurService {
    @Autowired
    utilisateurMysqlRepository repository;

    public utilisateur getUtilisateurById(int id) {
        return repository.findById(id).get();
    }

    public utilisateur getUtilisateurByUsername(String username) {
        return repository.findByUsername(username);
    }
}
