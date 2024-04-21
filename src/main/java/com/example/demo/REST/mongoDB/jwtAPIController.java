package com.example.demo.REST.mongoDB;

import com.example.demo.util.JwtUtil;
import com.example.demo.database.mongoDB.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
public class jwtAPIController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UtilisateurServ utilisateurServ;

    @GetMapping
    public String getMappingForUtilisateur(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        var utilisateurName = utilisateurServ.verifyCredentials(username, password);
        if (utilisateurName == null) {
            return "User not found Or Invalid Credentials";
        }

        return jwtUtil.generateToken(utilisateurName);
    }

    @PostMapping
    public String verifyToken(@RequestParam String token) {
        if(jwtUtil.isTokenValid(token)) {
            var utilisateur = utilisateurServ.getUtilisateurByNom(jwtUtil.extractUsername(token));
            return "Valid token for user: " + utilisateur.getNom();
        }
        return "Invalid token";
    }
}
