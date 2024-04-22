package com.example.demo.REST.mongoDB;

import com.example.demo.database.mongoDB.model.Inscription;
import com.example.demo.database.mongoDB.service.InscriptionServ;
import com.example.demo.database.mongoDB.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongoDB/inscription")
public class inscriptionAPIController {
    @Autowired
    InscriptionServ inscriptionServ;
    @Autowired
    UtilisateurServ utilisateurServ;

    @GetMapping
    public List<Inscription> getAllInscriptions() {
        return inscriptionServ.getAllInscriptions();
    }

    @GetMapping("/byId{inscriptionId}")
    public Inscription getInscription(@RequestParam(name = "inscriptionId") String inscriptionId) {
        return inscriptionServ.getInscriptionById(inscriptionId);
    }

    @PostMapping
    public boolean addInscription(@RequestBody Inscription inscription) {
        var utilisateur = utilisateurServ.getUtilisateurById(inscription.getUtilisateurId());
        inscription.setUtilisateur(utilisateur);
        return inscriptionServ.insertInscription(inscription);
    }

    @DeleteMapping
    public boolean deleteInscription(@RequestParam(name = "inscriptionId") String inscriptionId) {
        Inscription inscription = inscriptionServ.getInscriptionById(inscriptionId);
        if (inscription == null) {
            return false;
        }
        return inscriptionServ.deleteInscription(inscription);
    }
}
