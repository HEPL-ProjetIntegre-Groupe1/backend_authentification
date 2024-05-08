package com.example.demo.ORM.service;

import com.example.demo.ORM.interfaceP.UtilisateurRepository;
import com.example.demo.ORM.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServ {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(String id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
    public Utilisateur getUtilisateurByNom(String nom) {
        return utilisateurRepository.findUtilisateurByNom(nom);
    }
    public Utilisateur getUtilisateurByRegistreNational(String registreNational) {return utilisateurRepository.findUtilisateurByRegistreNational(registreNational);}
    public String inscriptionUtilisateur(Utilisateur utilisateur) {
        Utilisateur u = getUtilisateurByRegistreNational(utilisateur.getRegistreNational());
        if(u != null) {
            return null;
        }
        utilisateurRepository.save(utilisateur);
        return utilisateur.getId();
    }

    public String insertUtilisateur(Utilisateur utilisateur) {
        try {
            utilisateurRepository.save(utilisateur);
            return utilisateur.getId();
        } catch (Exception e) {
            return null;
        }
    }


    public boolean deleteUtilisateur(Utilisateur utilisateur) {
        try {
            utilisateurRepository.delete(utilisateur);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String verifyCredentials(String username, String password) {
        var utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        if(utilisateur != null && utilisateur.getPassword().equals(password)) {
            return utilisateur.getNom();
        }
        return null;
    }
}
