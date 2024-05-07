package com.example.demo.REST;


import com.example.demo.ORM.model.Authentication;
import com.example.demo.ORM.service.AuthenticationServ;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mongoDB/authentication")
public class authenticationAPIController {
    @Autowired
    private AuthenticationServ authenticationServ;

    @GetMapping
    public List<Authentication> getAllAuthentications() {
        var t = authenticationServ.getAllAuthentications();
        return t;
    }

    @PutMapping
    public String truc2(@RequestBody Map<String, String> body) {
        var method = body.get("method");
        var registreNational = body.get("registreNational");

        if(method == null || registreNational == null) {
            return "Error";
        }

        switch (method) {
            case "EID":
                System.out.println("A changer : réponse correcte HTTP (authenticationAPIController)");
                var object = authenticationServ.requestAuthenticationEID(registreNational);
                return object.get(1).toString();
            case "RFID":
                return authenticationServ.requestAuthenticationRFID(registreNational);
            case "SMSEMAIL":
                return authenticationServ.requestAuthenticationSMSEMAIL(registreNational);
            case "MasiId":
                return authenticationServ.requestAuthenticationMasiId(registreNational);
            default:
                return "Error";
        }

    }

    @PutMapping("/{authenticationId}")
    public String truc(@PathVariable String authenticationId, @RequestBody bodyContent body) {
        // RFID
        if (body.getCODE() != null) {
            return authenticationServ.verifyAuthenticationRFID(authenticationId, body.getCODE());
        }
        // SMS/EMAIL
        else if (body.getDigest() != null) {
            return authenticationServ.verifyAuthenticationSMSEMAIL(authenticationId, body.getDigest());
        }
        // MasiId
        else if (body.getIcon() != null) {
            return authenticationServ.verifyAuthenticationMasiId(authenticationId, body.getIcon());
        }
        // EID
        else if (body.getChallenge() != null) {
            return authenticationServ.verifyAuthenticationEID(authenticationId, body.getChallenge());
        }
        return "Error";
    }

    public static class bodyContent {
        @JsonProperty("CODE")
        private String CODE;
        @JsonProperty("Digest")
        private String Digest;
        @JsonProperty("Icon")
        private String Icon;
        @JsonProperty("challenge")
        private String challenge;

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getDigest() {
            return Digest;
        }

        public void setDigest(String digest) {
            Digest = digest;
        }

        public String getIcon() {
            return Icon;
        }

        public void setIcon(String icon) {
            Icon = icon;
        }

        public String getChallenge() {
            return challenge;
        }

        public void setChallenge(String challenge) {
            this.challenge = challenge;
        }

        // getters and setters
    }
}
