package com.example.demo.ORM.service;

import com.example.demo.ORM.interfaceP.AuthenticationRepository;
import com.example.demo.ORM.interfaceP.UtilisateurRepository;
import com.example.demo.ORM.model.Authentication;
import com.example.demo.ORM.model.Challenge;
import com.example.demo.ORM.model.Utilisateur;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthenticationServ {
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private ChallengeServ challengeServ;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RegistrationServ registrationServ;

    public List<Authentication> getAllAuthentications() {
        return authenticationRepository.findAll();
    }

    public Authentication getAuthenticationById(String id) {
        return authenticationRepository.findById(id).orElse(null);
    }

    public Authentication getOngoingAuthenticationByResgistreNational(String registreNational) {
        List<Authentication> auths =  authenticationRepository.getAuthenticationsByRegistreNationalAndOnGoingEquals(registreNational, true);
        if(auths.isEmpty())
            return null;
        return auths.get(0);
    }

    public boolean deleteAuthentication(Authentication authentication) {
        if(authentication != null) {
            authenticationRepository.delete(authentication);
            return true;
        }
        return false;
    }

    private boolean isAuthenticationRequestAllowed(String registreNational) {
        // Si une inscription est en cours, l'utilisateur doit d'abord finir de s'authentifier par EID
        if(registrationServ.getRegistrationByRegistreNational(registreNational) != null) {
            return false;
        }

        Authentication previousAuth = getOngoingAuthenticationByResgistreNational(registreNational);
        if(previousAuth != null) {
            challengeServ.deleteChallenge(previousAuth.getChallengeRef());
            authenticationRepository.delete(previousAuth);
        }
        return true;
    }

    private boolean isAuthenticationVerificationAllowed(Authentication authentication) {
        // Si une inscription est en cours, l'utilisateur doit d'abord finir de s'authentifier par EID
        return registrationServ.getRegistrationByRegistreNational(authentication.getRegistreNational()) != null;
    }

    public Map<String, String> requestAuthenticationEID(String registreNational, String device) {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        Authentication auth = new Authentication();

        Challenge challenge = new Challenge();
        challenge.setUncryptedMessage("1234");
        challengeServ.insertChallenge(challenge);

        auth.setType("EID");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        auth.setDeviceConnexion(device);
        auth.setOnGoing(true);

        authenticationRepository.save(auth);

        // A CHANGER : créer un message crypté par la clé publique
        System.out.println("Besoin de crypter le message (AuthenticationServ)");
        return Map.of("idAuthentication", auth.getId(), "registreNational",registreNational, "message", "Le message est 1234 (budget crypto limité...)");
    }

    public Map<String, String> verifyAuthenticationEID(String registreNational, String message) {
        Authentication auth = getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null) {
            return null;
        }

        // A CHANGER : décrypter le message avec la clé privée
        System.out.println("Besoin de décrypter le message (AuthenticationServ)");
        var actualMessage = auth.getChallengeRef().getUncryptedMessage();
        if(!auth.getType().equals("EID") ||!actualMessage.equals(message)) {
            return null;
        }

        // On termine l'authentification
        finishAuthentification(auth);

        // S'il y a une inscription en cours, on la termine
        registrationServ.deleteRegistration(registrationServ.getRegistrationByRegistreNational(auth.getRegistreNational()));

        return Map.of("JWT", getJwtTokenFromAuthentication(auth));
    }

    public Map<String, String> requestAuthenticationRFID(String registreNational, String device) {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        Authentication auth = new Authentication();

        System.out.println("Besoin de générer un challenge (AuthenticationServ)");
        Challenge challenge = new Challenge();
        challenge.setCode("1234");
        challengeServ.insertChallenge(challenge);

        auth.setType("RFID");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        auth.setDeviceConnexion(device);
        auth.setOnGoing(true);

        authenticationRepository.save(auth);

        return Map.of("idAuthentication", auth.getId(), "registreNational",registreNational, "message", "Le code est 1234 (budget crypto limité...)");
    }

    public Map<String, String> verifyAuthenticationRFID(String registreNational, String code) {
        Authentication auth =  getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        if(!auth.getType().equals("RFID") ||!auth.getChallengeRef().getCode().equals(code)) {
            return null;
        }

        // On termine l'authentification
        finishAuthentification(auth);

        return Map.of("JWT", getJwtTokenFromAuthentication(auth));
    }

    public Map<String, String> requestAuthenticationSMSEMAIL(String registreNational, String device) {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        Authentication auth = new Authentication();

        System.out.println("Besoin de générer un challenge (AuthenticationServ)");
        Challenge challenge = new Challenge();
        challenge.setCode("1234");
        challengeServ.insertChallenge(challenge);

        auth.setType("SMSEMAIL");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        auth.setDeviceConnexion(device);
        auth.setOnGoing(true);

        authenticationRepository.save(auth);

        System.out.println("Besoin d'envoyer un message (AuthenticationServ)");

        return Map.of("idAuthentication", auth.getId(), "registreNational",registreNational, "message", "Le code est 1234");
    }

    public Map<String, String> verifyAuthenticationSMSEMAIL(String registreNational, String code) {
        Authentication auth = getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        if(!auth.getType().equals("SMSEMAIL") ||!auth.getChallengeRef().getCode().equals(code)) {
            return null;
        }

        // On termine l'authentification
        finishAuthentification(auth);


        return Map.of("JWT", getJwtTokenFromAuthentication(auth));
    }

    public Map<String, String> requestAuthenticationMasiId(String registreNational, String device) {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        Authentication auth = new Authentication();

        System.out.println("Besoin de selectionner les images (AuthenticationServ)");
        Challenge challenge = new Challenge();
        challenge.setImages(List.of("2", "4", "7"));
        challenge.setRightImage("4");
        challengeServ.insertChallenge(challenge);

        auth.setType("MasiId");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        auth.setDeviceConnexion(device);
        auth.setOnGoing(true);

        authenticationRepository.save(auth);

        return Map.of("idAuthentication", auth.getId(), "registreNational",registreNational, "message", "Choix entre 2,4,7 (correct : 4)");
    }

    public Map<String, String> verifyAuthenticationMasiId(String registreNational, String image) {
        Authentication auth = getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        if(!auth.getType().equals("MasiId") || !auth.getChallengeRef().getRightImage().equals(image)) {
            return null;
        }

        // On termine l'authentification
        finishAuthentification(auth);


        return Map.of("JWT", getJwtTokenFromAuthentication(auth));
    }


    private String getJwtTokenFromAuthentication(Authentication auth) {
        Utilisateur u = utilisateurRepository.findUtilisateurByRegistreNational(auth.getRegistreNational());

        return jwtUtil.generateToken(u.getUsername());
    }

    public boolean requestRegistration(String registreNational) {
        return registrationServ.requestRegistration(registreNational) != null;
    }

    private void finishAuthentification(Authentication auth) {
        auth.setOnGoing(false);
        authenticationRepository.save(auth);
    }
}
