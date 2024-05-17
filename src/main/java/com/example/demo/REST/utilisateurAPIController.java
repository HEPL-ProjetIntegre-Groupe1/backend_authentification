package com.example.demo.REST;

import com.example.demo.ORM.model.Utilisateur;
import com.example.demo.ORM.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateur")
public class utilisateurAPIController {
    @Autowired
    UtilisateurServ utilisateurServ;

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getUser(@RequestParam(name = "userId", required = false) String userId, @RequestParam(name = "nom", required = false) String nom, @RequestParam(name="registreNational", required = false) String registreNational) {
        if(userId != null) {
            var utilisateur = utilisateurServ.getUtilisateurById(userId);
            if(utilisateur == null) {
                return ResponseEntity.badRequest().body(List.of());
            }
            return ResponseEntity.ok(List.of(utilisateur));
        }
        if(nom != null) {
            var utilisateur = utilisateurServ.getUtilisateurByNom(nom);
            if(utilisateur == null) {
                return ResponseEntity.badRequest().body(List.of());
            }
            return ResponseEntity.ok(List.of(utilisateur));
        }
        if(registreNational != null) {
            var utilisateur = utilisateurServ.getUtilisateurById(registreNational);
            if(utilisateur == null) {
                return ResponseEntity.badRequest().body(List.of());
            }
            return ResponseEntity.ok(List.of(utilisateur));
        }

        var Utilisateurs = utilisateurServ.getAllUtilisateurs();
        return ResponseEntity.ok(Utilisateurs);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody Utilisateur utilisateur) {
        var id = utilisateurServ.insertUtilisateur(utilisateur);
        if(id != null)
            return ResponseEntity.ok(id);
        return ResponseEntity.badRequest().body("Could not add user");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam(name = "userId") String userId) {
        Utilisateur utilisateur = utilisateurServ.getUtilisateurById(userId);
        if (utilisateur == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        if(utilisateurServ.deleteUtilisateur(utilisateur))
            return ResponseEntity.ok("User deleted");
        return ResponseEntity.badRequest().body("Could not delete user");
    }

    @PutMapping
    public ResponseEntity<String> isUserSigningIn(@RequestParam(name = "registreNational") String registreNational) {
        if(utilisateurServ.isUserSigningUp(registreNational))
            return ResponseEntity.ok("User is currently signing up");
        return ResponseEntity.ok("User is not currently signing up");
    }
}
