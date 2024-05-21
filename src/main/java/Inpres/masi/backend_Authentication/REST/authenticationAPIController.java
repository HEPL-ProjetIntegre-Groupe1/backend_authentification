package Inpres.masi.backend_Authentication.REST;


import Inpres.masi.backend_Authentication.ORM.service.AuthenticationServ;
import Inpres.masi.backend_Authentication.util.ExportDatabase;
import Inpres.masi.backend_Authentication.ORM.model.Authentication;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;
import org.json.JSONObject;
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
            var authentication = authenticationServ.getOngoingAuthenticationByResgistreNational(registreNational);
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
    public ResponseEntity<String> requeteAuthentication(@RequestBody Map<String, String> body) throws JSONException {
        var method = body.get("method");
        var registreNational = body.get("registreNational");
        var device = body.get("device");

        if(method == null || registreNational == null || device == null) {
            return new ResponseEntity<>(new JSONObject().put("Error", "Method or Registre National or device missing").toString(), HttpStatus.BAD_REQUEST);
        }

        JSONObject result;
        switch (method) {
            case "EID" -> result = authenticationServ.requestAuthenticationEID(registreNational,device);
            case "RFID" -> result = authenticationServ.requestAuthenticationRFID(registreNational,device);
            case "SMSEMAIL" -> result = authenticationServ.requestAuthenticationSMSEMAIL(registreNational,device);
            case "MasiId" -> result = authenticationServ.requestAuthenticationMasiId(registreNational,device);
            default -> {
                return new ResponseEntity<>(new JSONObject().put("Error","Wrong method").toString(), HttpStatus.BAD_REQUEST);
            }
        }

        if(result == null)
            return new ResponseEntity<>(new JSONObject().put("Error","Authentication request denied").toString(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    @PutMapping("/{registreNational}")
    public ResponseEntity<String> validationRequeteAuthentification(@PathVariable String registreNational, @RequestBody bodyContent body) throws JSONException {
        // RFID
        if (body.getCODE() != null) {
            var JWT = authenticationServ.verifyAuthenticationRFID(registreNational, body.getCODE());
            if (JWT != null) {
                return new ResponseEntity<>(JWT.toString(), HttpStatus.OK);
            }
        }
        // SMS/EMAIL
        else if (body.getDigest() != null) {
            var JWT = authenticationServ.verifyAuthenticationSMSEMAIL(registreNational, body.getDigest());
            if (JWT != null) {
                return new ResponseEntity<>(JWT.toString(), HttpStatus.OK);
            }
        }
        // MasiId
        else if (body.getIcon() != null) {
            var JWT = authenticationServ.verifyAuthenticationMasiId(registreNational, body.getIcon());
            if (JWT != null) {
                return new ResponseEntity<>(JWT.toString(), HttpStatus.OK);
            }
        }
        // EID
        else if (body.getChallenge() != null) {
            var JWT = authenticationServ.verifyAuthenticationEID(registreNational, body.getChallenge());
            if (JWT != null) {
                return new ResponseEntity<>(JWT.toString(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new JSONObject().put("Error","The validation method does not match the current authentication request (if any exists)").toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAuthentication(@RequestParam(name = "authenticationId") String authenticationId) {
        Authentication authentication = authenticationServ.getAuthenticationById(authenticationId);
        if (authentication == null) {
            return ResponseEntity.badRequest().body("Authentication not found");
        }
        if(authenticationServ.deleteAuthentication(authentication))
            return ResponseEntity.ok("Authentication deleted");
        return ResponseEntity.badRequest().body("Could not delete authentication");
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
    @RestController
    @RequestMapping("/databaseExport")
    public class databaseExport {
        @Autowired
        private ExportDatabase exportDatabase;

        @PostMapping
        public ResponseEntity<String> exportDatabase() {
            exportDatabase.filterDatabase();
            exportDatabase.exportDatabase();
            return ResponseEntity.ok("ok");
        }
    }
}
