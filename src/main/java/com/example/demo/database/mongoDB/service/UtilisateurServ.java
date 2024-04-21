package com.example.demo.database.mongoDB.service;

import com.example.demo.database.mongoDB.interfaceP.UtilisateurRepository;
import com.example.demo.database.mongoDB.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServ {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private CredentialsServ credentialsServ;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(String id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
    public boolean insertUtilisateur(Utilisateur utilisateur) {
        try {
            var credentials = credentialsServ.getCredentialsById(utilisateur.getCredentialsId());
            utilisateur.setCredentials(credentials);
            utilisateurRepository.save(utilisateur);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Utilisateur getUtilisateurByNom(String nom) {
        return utilisateurRepository.findUtilisateurByNom(nom);
    }

    public boolean deleteUtilisateur(Utilisateur utilisateur) {
        try {
            utilisateurRepository.delete(utilisateur);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Utilisateur getUserByCredentials(String username) {
        var credentials = credentialsServ.getCredentialsByUsername(username);
        if(credentials != null) {
            return utilisateurRepository.findUtilisateurByCredentialsRef(credentials);
        }
        return null;
    }
    public String verifyCredentials(String username, String password) {
        var utilisateur = getUserByCredentials(username);
        if(utilisateur != null && utilisateur.getCredentials().getPassword().equals(password)) {
            return utilisateur.getNom();
        }
        return null;
    }
}
