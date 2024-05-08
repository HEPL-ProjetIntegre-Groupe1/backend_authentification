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

    public Authentication getAuthenticationByRegistreNational(String registreNational) {
        return authenticationRepository.getAuthenticationByRegistreNational(registreNational);
    }

    public void deleteAuthentication(Authentication authentication) {
        if(authentication != null) {
            authenticationRepository.delete(authentication);
        }
    }

    private boolean isAuthenticationRequestAllowed(String registreNational) {
        // Si une inscription est en cours, l'utilisateur doit d'abord finir de s'authentifier par EID
        if(registrationServ.getRegistrationByRegistreNational(registreNational) != null) {
            return false;
        }

        Authentication previousAuth = getAuthenticationByRegistreNational(registreNational);
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

    public Map<String, String> requestAuthenticationEID(String registreNational) {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        Authentication auth = new Authentication();

        Challenge challenge = new Challenge();
        challenge.setUncryptedMessage("1234");
        challengeServ.insertChallenge(challenge);

        auth.setType("EID");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        authenticationRepository.save(auth);

        // A CHANGER : créer un message crypté par la clé publique
        System.out.println("Besoin de crypter le message (AuthenticationServ)");
        return Map.of("idAuthentication", auth.getId(), "message", "Le message est 1234 (budget crypto limité...)");
    }

    public String verifyAuthenticationEID(String id, String message) {
        Authentication auth = authenticationRepository.findById(id).orElse(null);

        if(auth == null) {
            return null;
        }

        // A CHANGER : décrypter le message avec la clé privée
        System.out.println("Besoin de décrypter le message (AuthenticationServ)");
        var actualMessage = auth.getChallengeRef().getUncryptedMessage();
        if(!auth.getType().equals("EID") ||!actualMessage.equals(message)) {
            return null;
        }

        // S'il y a une inscription en cours, on la termine
        registrationServ.deleteRegistration(registrationServ.getRegistrationByRegistreNational(auth.getRegistreNational()));

        return getJwtTokenFromAuthentication(auth);
    }

    public String requestAuthenticationRFID(String registreNational) {
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

        authenticationRepository.save(auth);

        return auth.getId();
    }

    public String verifyAuthenticationRFID(String idAuthentification, String code) {
        Authentication auth = authenticationRepository.findById(idAuthentification).orElse(null);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        if(!auth.getType().equals("RFID") ||!auth.getChallengeRef().getCode().equals(code)) {
            return null;
        }

        return getJwtTokenFromAuthentication(auth);
    }

    public String requestAuthenticationSMSEMAIL(String registreNational) {
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

        authenticationRepository.save(auth);

        System.out.println("Besoin d'envoyer un message (AuthenticationServ)");

        return auth.getId();
    }

    public String verifyAuthenticationSMSEMAIL(String id, String code) {
        Authentication auth = authenticationRepository.findById(id).orElse(null);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        if(!auth.getType().equals("SMSEMAIL") ||!auth.getChallengeRef().getCode().equals(code)) {
            return null;
        }

        return getJwtTokenFromAuthentication(auth);
    }

    public String requestAuthenticationMasiId(String registreNational) {
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

        authenticationRepository.save(auth);

        return auth.getId();
    }

    public String verifyAuthenticationMasiId(String id, String image) {
        Authentication auth = authenticationRepository.findById(id).orElse(null);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        if(!auth.getType().equals("MasiId") || !auth.getChallengeRef().getRightImage().equals(image)) {
            return null;
        }

        return getJwtTokenFromAuthentication(auth);
    }


    private String getJwtTokenFromAuthentication(Authentication auth) {
        Utilisateur u = utilisateurRepository.findUtilisateurByRegistreNational(auth.getRegistreNational());

        return jwtUtil.generateToken(u.getUsername());
    }

    public boolean requestRegistration(String registreNational) {
        return registrationServ.requestRegistration(registreNational) != null;
    }
}
