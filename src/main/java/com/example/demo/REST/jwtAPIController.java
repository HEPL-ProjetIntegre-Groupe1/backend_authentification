package com.example.demo.REST;

import com.example.demo.util.JwtUtil;
import com.example.demo.ORM.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
public class jwtAPIController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UtilisateurServ utilisateurServ;

    @GetMapping
    public ResponseEntity<String> getMappingForUtilisateur(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        var utilisateurName = utilisateurServ.verifyCredentials(username, password);
        if (utilisateurName == null) {
            return ResponseEntity.badRequest().body("Logging attempt denied");
        }

        var JWT = jwtUtil.generateToken(utilisateurName);
        return ResponseEntity.ok(JWT);
    }

    @PostMapping
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        if(jwtUtil.isTokenValid(token)) {
            var utilisateur = utilisateurServ.getUtilisateurByNom(jwtUtil.extractUsername(token));
            return ResponseEntity.ok("Valid token for user: " + utilisateur.getNom());
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }
}
