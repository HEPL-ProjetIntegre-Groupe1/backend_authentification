package com.example.demo.REST;

import com.example.demo.ORM.model.Utilisateur;
import com.example.demo.ORM.service.UtilisateurServ;
import org.json.JSONException;
import org.json.JSONObject;
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
    public ResponseEntity<String> inscriptionUtilisateur(@RequestParam(name = "device")String device, @RequestBody Utilisateur utilisateur) throws JSONException {
        if(device == null)
            device = "Computer";
        var reponse = utilisateurServ.inscriptionUtilisateur(utilisateur, device);
        if(reponse != null)
            return ResponseEntity.ok(reponse.toString());
        return ResponseEntity.badRequest().body(new JSONObject().put("Error", "User already exists").toString());
    }
}
