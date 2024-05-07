package com.example.demo.ORM.service;

import com.example.demo.ORM.interfaceP.AuthenticationRepository;
import com.example.demo.ORM.model.Authentication;
import com.example.demo.ORM.model.Challenge;
import com.example.demo.ORM.model.Utilisateur;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServ {
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private ChallengeServ challengeServ;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UtilisateurServ utilisateurServ;

    public List<Authentication> getAllAuthentications() {
        return authenticationRepository.findAll();
    }

    public List<Object> requestAuthenticationEID(String registreNational) {
        Authentication auth = new Authentication();

        Challenge challenge = new Challenge();
        challenge.setUncryptedMessage("1234");

        auth.setType("EID");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        authenticationRepository.save(auth);

        // A CHANGER : créer un message crypté par la clé publique
        System.out.println("Besoin de crypter le message (AuthenticationServ)");
        List<Object> list = List.of(auth.getId(), "Ce message est crypté");

        return list;
    }

    public String verifyAuthenticationEID(String id, String message) {
        Authentication auth = authenticationRepository.findById(id).orElse(null);

        if(auth == null) {
            return null;
        }

        // A CHANGER : décrypter le message avec la clé privée
        System.out.println("Besoin de décrypter le message (AuthenticationServ)");
        var actualMessage = auth.getChallengeRef().getUncryptedMessage();
        if(!actualMessage.equals(message)) {
            return null;
        }

        return getJwtTokenFromAuthentication(auth);
    }

    public String requestAuthenticationRFID(String registreNational) {
        Authentication auth = new Authentication();

        System.out.println("Besoin de générer un challenge (AuthenticationServ)");
        Challenge challenge = new Challenge();
        challenge.setCode("1234");

        auth.setType("RFID");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);

        authenticationRepository.save(auth);

        return auth.getId();
    }

    public String verifyAuthenticationRFID(String id, String code) {
        Authentication auth = authenticationRepository.findById(id).orElse(null);

        if(auth == null) {
            return null;
        }

        if(!auth.getChallengeRef().getCode().equals(code)) {
            return null;
        }

        return getJwtTokenFromAuthentication(auth);
    }

    public String requestAuthenticationSMSEMAIL(String registreNational) {
        Authentication auth = new Authentication();

        System.out.println("Besoin de générer un challenge (AuthenticationServ)");
        Challenge challenge = new Challenge();
        challenge.setCode("1234");

        auth.setType("SMSEMAIL");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);

        authenticationRepository.save(auth);

        System.out.println("Besoin d'envoyer un message (AuthenticationServ)");

        return auth.getId();
    }

    public String verifyAuthenticationSMSEMAIL(String id, String code) {
        Authentication auth = authenticationRepository.findById(id).orElse(null);

        if(auth == null) {
            return null;
        }

        if(!auth.getChallengeRef().getCode().equals(code)) {
            return null;
        }

        return getJwtTokenFromAuthentication(auth);
    }

    public String requestAuthenticationMasiId(String registreNational) {
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

        if(auth == null) {
            return null;
        }

        if(!auth.getChallengeRef().getRightImage().equals(image)) {
            return null;
        }

        return getJwtTokenFromAuthentication(auth);
    }


    private String getJwtTokenFromAuthentication(Authentication auth) {
        Utilisateur u = utilisateurServ.getUtilisateurByRegistreNational(auth.getRegistreNational());

        return jwtUtil.generateToken(u.getUsername());
    }
}
