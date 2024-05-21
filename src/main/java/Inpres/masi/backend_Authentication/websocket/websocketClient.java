package Inpres.masi.backend_Authentication.websocket;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Service
public class websocketClient {
    private clientWebsocketHandler client;
    @PostConstruct
    public void connect() {
        this.client = new clientWebsocketHandler();

        StandardWebSocketClient client = new StandardWebSocketClient();
        client.doHandshake(this.client, "ws://localhost:8080/ws");
    }

    public void sendMessage(String message) {
        this.client.sendMessage(message);
    }

    private class clientWebsocketHandler extends TextWebSocketHandler {
        private WebSocketSession session;
        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            System.out.println("Connexion établie");
            this.session = session;
        }

        public void sendMessage(String message) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
