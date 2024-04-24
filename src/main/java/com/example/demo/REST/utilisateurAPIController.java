package com.example.demo.REST;

import com.example.demo.ORM.model.Utilisateur;
import com.example.demo.ORM.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongoDB/utilisateur")
public class utilisateurAPIController {
    @Autowired
    UtilisateurServ utilisateurServ;

    @GetMapping
    public List<Utilisateur> getAllUsers() {
        return utilisateurServ.getAllUtilisateurs();
    }

    @GetMapping("/byId")
    public Utilisateur getUser(@RequestParam(name = "userId") String userId) {
        return utilisateurServ.getUtilisateurById(userId);
    }
    @GetMapping("/byName")
    public Utilisateur getUserByUsername(@RequestParam(name = "nom") String nom) {
        return utilisateurServ.getUtilisateurByNom(nom);
    }

    @PostMapping
    public boolean addUser(@RequestBody Utilisateur utilisateur) {
        return utilisateurServ.insertUtilisateur(utilisateur);
    }

    @DeleteMapping
    public boolean deleteUser(@RequestParam(name = "userId") String userId) {
        Utilisateur utilisateur = utilisateurServ.getUtilisateurById(userId);
        if (utilisateur == null) {
            return false;
        }
        return utilisateurServ.deleteUtilisateur(utilisateur);
    }
}
