package com.example.demo.ORM.service;

import com.example.demo.ORM.interfaceP.UtilisateurRepository;
import com.example.demo.ORM.model.Registration;
import com.example.demo.ORM.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UtilisateurServ {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private AuthenticationServ authenticationServ;
    @Autowired
    private RegistrationServ registrationServ;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }
    public Utilisateur getUtilisateurById(String id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
    public Utilisateur getUtilisateurByNom(String nom) {
        return utilisateurRepository.findUtilisateurByNom(nom);
    }
    public Map<String, String> inscriptionUtilisateur(Utilisateur utilisateur) {
        // Un utilisateur ne peut pas s'inscrire deux fois
        Utilisateur u = getUtilisateurById(utilisateur.getRegistreNational());
        if(u != null) {
            return null;
        }
        utilisateurRepository.save(utilisateur);

        // A sa première inscription, l'utilisateur doit s'authentifier par EID
        var reponse = authenticationServ.requestAuthenticationEID(utilisateur.getRegistreNational());

        // On ajoute une requête d'inscription pour l'utilisateur
        authenticationServ.requestRegistration(utilisateur.getRegistreNational());

        return reponse;
    }

    public String insertUtilisateur(Utilisateur utilisateur) {
        try {
            utilisateurRepository.save(utilisateur);
            return utilisateur.getRegistreNational();
        } catch (Exception e) {
            return null;
        }
    }


    public boolean deleteUtilisateur(Utilisateur utilisateur) {
        try {
            // Supprimer la registration
            Registration reg = registrationServ.getRegistrationByRegistreNational(utilisateur.getRegistreNational());
            if(reg != null) {
                registrationServ.deleteRegistration(reg);
            }

            // Supprimer l'authentification
            var auth = authenticationServ.getAuthenticationByRegistreNational(utilisateur.getRegistreNational());
            if(auth != null) {
                authenticationServ.deleteAuthentication(auth);
            }

            // Supprimer l'utilisateur
            utilisateurRepository.delete(utilisateur);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String verifyCredentials(String username, String password) {
        var utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        // Si l'utilisateur n'existe pas ou que le mot de passe est incorrect ou qu'il est en cours d'inscription
        if(utilisateur == null || !utilisateur.getPassword().equals(password) || registrationServ.getRegistrationByRegistreNational(utilisateur.getRegistreNational()) != null) {
            return null;
        }

        return utilisateur.getNom();
    }

    public boolean isUserSigningUp(String registreNational) {
        return registrationServ.getRegistrationByRegistreNational(registreNational) != null;
    }
}
