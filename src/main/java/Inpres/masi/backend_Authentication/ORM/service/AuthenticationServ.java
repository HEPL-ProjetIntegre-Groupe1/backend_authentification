package Inpres.masi.backend_Authentication.ORM.service;

import Inpres.masi.backend_Authentication.Crypto.CryptoUtil;
import Inpres.masi.backend_Authentication.ORM.interfaceP.AuthenticationRepository;
import Inpres.masi.backend_Authentication.ORM.interfaceP.UtilisateurRepository;
import Inpres.masi.backend_Authentication.ORM.model.Challenge;
import Inpres.masi.backend_Authentication.ORM.model.Utilisateur;
import Inpres.masi.backend_Authentication.ORM.model.Authentication;
import Inpres.masi.backend_Authentication.Twilio.SmsController;
import Inpres.masi.backend_Authentication.util.JwtUtil;
import Inpres.masi.backend_Authentication.util.randomGenerator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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
    @Autowired
    private randomGenerator randomGenerator;
    @Autowired
    private SmsController smsController;

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
        if(utilisateurRepository.findUtilisateurByRegistreNational(registreNational) == null)
            return false;

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

    public JSONObject requestAuthenticationEID(String registreNational, String device) throws JSONException {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        Authentication auth = new Authentication();

        Challenge challenge = new Challenge();
        var uncryptedMessage = randomGenerator.generateRandomString();
        challenge.setUncryptedMessage(uncryptedMessage);
        challengeServ.insertChallenge(challenge);

        String cryptedMessage = encryptFromRegistreNational(registreNational, uncryptedMessage);

        auth.setType("EID");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        auth.setDeviceConnexion(device);
        auth.setOnGoing(true);

        authenticationRepository.save(auth);

        return new JSONObject()
                .put("idAuthentication", auth.getId())
                .put("registreNational",registreNational)
                .put("message", String.format("%s", cryptedMessage));
    }

    public JSONObject verifyAuthenticationEID(String registreNational, String message) throws JSONException {
        Authentication auth = getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null) {
            return null;
        }

        var actualMessage = auth.getChallengeRef().getUncryptedMessage();
        if(!auth.getType().equals("EID") ||!actualMessage.equals(message)) {
            return null;
        }

        // On termine l'authentification
        finishAuthentification(auth);

        // S'il y a une inscription en cours, on la termine
        registrationServ.deleteRegistration(registrationServ.getRegistrationByRegistreNational(auth.getRegistreNational()));


        return new JSONObject()
                .put("JWT", getJwtTokenFromAuthentication(auth));
    }

    public JSONObject requestAuthenticationRFID(String registreNational, String device) throws JSONException {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        Authentication auth = new Authentication();
        Challenge challenge = new Challenge();

        // On génère un code aléatoire
        var code = randomGenerator.generateRandomCode();
        challenge.setCode(code);

        challengeServ.insertChallenge(challenge);

        auth.setType("RFID");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        auth.setDeviceConnexion(device);
        auth.setOnGoing(true);

        authenticationRepository.save(auth);

        return new JSONObject()
                .put("idAuthentication", auth.getId())
                .put("registreNational",registreNational)
                .put("message", "Le code est " + code);
    }

    public JSONObject verifyAuthenticationRFID(String registreNational, String code) throws JSONException {
        Authentication auth =  getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        if(!auth.getType().equals("RFID") ||!auth.getChallengeRef().getCode().equals(code)) {
            return null;
        }

        // On termine l'authentification
        finishAuthentification(auth);


        return new JSONObject()
                .put("JWT", getJwtTokenFromAuthentication(auth));
    }

    public JSONObject requestAuthenticationSMSEMAIL(String registreNational, String device) throws JSONException {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        Authentication auth = new Authentication();
        Challenge challenge = new Challenge();

        var code = randomGenerator.generateRandomCode();
        challenge.setCode(code);

        challengeServ.insertChallenge(challenge);

        auth.setType("SMSEMAIL");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        auth.setDeviceConnexion(device);
        auth.setOnGoing(true);

        authenticationRepository.save(auth);

        var phone = utilisateurRepository.findUtilisateurByRegistreNational(registreNational).getNumeroTelephone();
        smsController.sendSms(phone, code);

        return new JSONObject()
                .put("idAuthentication", auth.getId())
                .put("registreNational",registreNational)
                .put("message", "Le code est " + code);
    }

    public JSONObject verifyAuthenticationSMSEMAIL(String registreNational, String code) throws JSONException {
        Authentication auth = getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        if(!auth.getType().equals("SMSEMAIL") ||!auth.getChallengeRef().getCode().equals(code)) {
            return null;
        }

        // On termine l'authentification
        finishAuthentification(auth);


        return new JSONObject()
                .put("JWT", getJwtTokenFromAuthentication(auth));
    }

    public JSONObject requestAuthenticationMasiId(String registreNational, String device) throws JSONException {
        if(!isAuthenticationRequestAllowed(registreNational))
            return null;

        // On génère l'authentification et son challenge
        Authentication auth = new Authentication();
        Challenge challenge = new Challenge();

        // On génère 3 images aléatoires
        var images = randomGenerator.getRandomImages();
        challenge.setImages(images);

        // On en sélectionne une aléatoirement
        var imageSelected = images.get(new Random().nextInt(images.size()));
        challenge.setRightImage(imageSelected);
        challengeServ.insertChallenge(challenge);

        auth.setType("MasiId");
        auth.setChallengeRef(challenge);
        auth.setRegistreNational(registreNational);
        auth.setDeviceConnexion(device);
        auth.setOnGoing(true);

        authenticationRepository.save(auth);

        return new JSONObject()
                .put("idAuthentication", auth.getId())
                .put("registreNational",registreNational)
                .put("message", String.format("Choix entre %s (correct : %s)",images.toString(), imageSelected));
    }

    public boolean sendImageChoiceMasiId(String registreNational, String imageSelected) {
        Authentication auth = getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null) {
            return false;
        }

        var actualMessage = auth.getChallengeRef().getRightImage();
        if(!auth.getType().equals("MasiId") ||!actualMessage.equals(imageSelected)) {
            return false;
        }

        // MasiId a validé le challenge. L'utilisateur web doit maintenant récupérer son JWT
        auth.getChallengeRef().setMasiIdValidated(true);
        challengeServ.insertChallenge(auth.getChallengeRef());

        return true;
    }

    public JSONObject verifyAuthenticationMasiId(String registreNational) throws JSONException {
        Authentication auth = getOngoingAuthenticationByResgistreNational(registreNational);

        if(auth == null || isAuthenticationVerificationAllowed(auth)) {
            return null;
        }

        // On vérifie si l'app MasiId a validé l'authentification
        if(!auth.getType().equals("MasiId") || !auth.getChallengeRef().isMasiIdValidated()) {
            return null;
        }

        // On termine l'authentification
        finishAuthentification(auth);

        return new JSONObject()
                .put("JWT", getJwtTokenFromAuthentication(auth));
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

    public Integer getComputerAuthenticationsByRegistreNational(String registreNational) {
        return authenticationRepository.getAuthenticationsByRegistreNationalAndDeviceConnexionEquals(registreNational, "Computer").size();
    }
    public Integer getPhoneAuthenticationsByRegistreNational(String registreNational) {
        return authenticationRepository.getAuthenticationsByRegistreNationalAndDeviceConnexionEquals(registreNational, "Phone").size();
    }

    private String encryptFromRegistreNational(String registreNational, String message) {
        try {
            var certificat = utilisateurRepository.findUtilisateurByRegistreNational(registreNational).getCertificat();
            var pub = CryptoUtil.buildPublicKey(certificat);
            var bytes = CryptoUtil.encrypt(message, pub);
            return CryptoUtil.byteTobase64(bytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
