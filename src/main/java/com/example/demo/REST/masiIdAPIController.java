package com.example.demo.REST;

import com.example.demo.ORM.model.Utilisateur;
import com.example.demo.ORM.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/masiId")
public class masiIdAPIController {
    @Autowired
    UtilisateurServ utilisateurServ;

    @PutMapping
    public ResponseEntity<String> inscriptionUtilisateur(@RequestBody Utilisateur utilisateur) {
        var id = utilisateurServ.inscriptionUtilisateur(utilisateur);
        if(id != null)
            return ResponseEntity.ok(id);
        return ResponseEntity.badRequest().body("Registre National already exists. Sign up failed.");
    }
}
