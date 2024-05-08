package com.example.demo.REST;

import com.example.demo.ORM.model.Utilisateur;
import com.example.demo.ORM.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/masiId")
public class masiIdAPIController {
    @Autowired
    UtilisateurServ utilisateurServ;

    @PutMapping
    public ResponseEntity<Map<String, String>> inscriptionUtilisateur(@RequestBody Utilisateur utilisateur) {
        var reponse = utilisateurServ.inscriptionUtilisateur(utilisateur);
        if(reponse != null)
            return ResponseEntity.ok(reponse);
        return ResponseEntity.badRequest().body(Map.of("error", "User already exists"));
    }
}
