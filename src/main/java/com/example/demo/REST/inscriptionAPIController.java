package com.example.demo.REST;

import com.example.demo.ORM.model.Utilisateur;
import com.example.demo.ORM.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/masiId")
public class inscriptionAPIController {
    @Autowired
    UtilisateurServ utilisateurServ;

    @PutMapping
    public ResponseEntity<Map<String, String>> inscriptionUtilisateur(@RequestParam(name = "device")String device, @RequestBody Utilisateur utilisateur) {
        if(device == null)
            device = "Computer";
        var reponse = utilisateurServ.inscriptionUtilisateur(utilisateur, device);
        if(reponse != null)
            return ResponseEntity.ok(reponse);
        return ResponseEntity.badRequest().body(Map.of("error", "User already exists"));
    }
}
