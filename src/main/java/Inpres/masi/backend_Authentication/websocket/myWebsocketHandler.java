package Inpres.masi.backend_Authentication.websocket;

import Inpres.masi.backend_Authentication.ORM.service.AuthenticationServ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class myWebsocketHandler extends TextWebSocketHandler {
    @Autowired
    private AuthenticationServ authenticationServ;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws JSONException {
        // Le client demande les données d'un registre national
        System.out.println("Message reçu : " + message.getPayload());

        var map = deserializeMessage(message.getPayload());

        // Get : On demande la liste d'image qu'on peut selectionner
        // Put : On envoie l'image selectionnée
        switch (map.get("requete")){
            case "GET" -> handleGetRequest(session, map.get("registreNational"));
            case "PUT" -> handlePutRequest(map.get("registreNational"), map.get("selectedImage"));
        }
    }

    private Map<String,String> deserializeMessage(String message) {
        Map<String, String> newMap = new HashMap<>();
        message = message.substring(1, message.length()-1); // Remove braces

        String[] entries = message.split(",");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            newMap.put(keyValue[0].trim(), keyValue[1].trim());
        }
        return newMap;
    }

    private void handleGetRequest(WebSocketSession session, String registreNational) {
        // On lui envoie les demande d'authentification
        var auth = authenticationServ.getOngoingAuthenticationByResgistreNational(registreNational);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonAuth = objectMapper.writeValueAsString(auth);
            session.sendMessage(new TextMessage(jsonAuth));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePutRequest(String registreNational, String selectedImage) throws JSONException {
        // On valide l'authentification
        authenticationServ.sendImageChoiceMasiId(registreNational, selectedImage);
    }
}
