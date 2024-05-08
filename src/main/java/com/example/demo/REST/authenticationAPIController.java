package com.example.demo.REST;


import com.example.demo.ORM.model.Authentication;
import com.example.demo.ORM.service.AuthenticationServ;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class authenticationAPIController {
    @Autowired
    private AuthenticationServ authenticationServ;

    @GetMapping
    public ResponseEntity<List<Authentication>> getAllAuthentications(@RequestParam(name = "authenticationId", required = false) String authenticationId, @RequestParam(name = "registreNational", required = false) String registreNational) {
        if (authenticationId != null) {
            var authentication = authenticationServ.getAuthenticationById(authenticationId);
            if(authentication == null) {
                return ResponseEntity.badRequest().body(List.of());
            }
            return ResponseEntity.ok(List.of(authentication));
        }
        if (registreNational != null) {
            var authentication = authenticationServ.getAuthenticationByRegistreNational(registreNational);
            if(authentication == null) {
                return ResponseEntity.badRequest().body(List.of());
            }
            return ResponseEntity.ok(List.of(authentication));
        }
        var authentications = authenticationServ.getAllAuthentications();
        if (authentications.isEmpty()) {
            return ResponseEntity.badRequest().body(authentications);
        }
        return ResponseEntity.ok(authentications);
    }

    @PutMapping
    public ResponseEntity<String> requeteAuthentication(@RequestBody Map<String, String> body) {
        var method = body.get("method");
        var registreNational = body.get("registreNational");

        if(method == null || registreNational == null) {
            return new ResponseEntity<>("Method or Registre National missing", HttpStatus.BAD_REQUEST);
        }

        String result;
        switch (method) {
            case "EID" -> result = authenticationServ.requestAuthenticationEID(registreNational).toString();

            case "RFID" -> result = authenticationServ.requestAuthenticationRFID(registreNational);
            case "SMSEMAIL" -> result = authenticationServ.requestAuthenticationSMSEMAIL(registreNational);
            case "MasiId" -> result = authenticationServ.requestAuthenticationMasiId(registreNational);
            default -> {
                return new ResponseEntity<>("Wrong method", HttpStatus.BAD_REQUEST);
            }
        }

        if(result == null)
            return new ResponseEntity<>("Authentication request denied", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{authenticationId}")
    public ResponseEntity<String> validationRequeteAuthentification(@PathVariable String authenticationId, @RequestBody bodyContent body) {
        // RFID
        if (body.getCODE() != null) {
            var JWT = authenticationServ.verifyAuthenticationRFID(authenticationId, body.getCODE());
            if (JWT != null) {
                return new ResponseEntity<>(JWT, HttpStatus.OK);
            }
        }
        // SMS/EMAIL
        else if (body.getDigest() != null) {
            var JWT = authenticationServ.verifyAuthenticationSMSEMAIL(authenticationId, body.getDigest());
            if (JWT != null) {
                return new ResponseEntity<>(JWT, HttpStatus.OK);
            }
        }
        // MasiId
        else if (body.getIcon() != null) {
            var JWT = authenticationServ.verifyAuthenticationMasiId(authenticationId, body.getIcon());
            if (JWT != null) {
                return new ResponseEntity<>(JWT, HttpStatus.OK);
            }
        }
        // EID
        else if (body.getChallenge() != null) {
            var JWT = authenticationServ.verifyAuthenticationEID(authenticationId, body.getChallenge());
            if (JWT != null) {
                return new ResponseEntity<>(JWT, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("The validation method does not match the current authentication request (if any exists)", HttpStatus.INTERNAL_SERVER_ERROR);
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
