package com.example.demo.websocket;

import com.example.demo.ORM.service.AuthenticationServ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class myWebsocketHandler extends TextWebSocketHandler {
    @Autowired
    private AuthenticationServ authenticationServ;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Le client demande les données d'un registre national
        System.out.println("Message reçu : " + message.getPayload());

        // On lui envoie les demande d'authentification
        var auth = authenticationServ.getOngoingAuthenticationByResgistreNational(message.getPayload());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonAuth = objectMapper.writeValueAsString(auth);
            session.sendMessage(new TextMessage(jsonAuth));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
