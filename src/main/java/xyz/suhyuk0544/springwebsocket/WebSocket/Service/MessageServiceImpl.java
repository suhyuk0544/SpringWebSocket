package xyz.suhyuk0544.springwebsocket.WebSocket.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static xyz.suhyuk0544.springwebsocket.WebSocket.Handlers.CustomWeSocketHandler.sessions;

@Service
public class MessageServiceImpl implements MessageService {


    public void sendAllMemberMessage(WebSocketSession session, TextMessage message) {

        sessions.values().forEach(s -> {
            if (!session.getId().equals(s.getId())) {
                try {
                    s.sendMessage(new TextMessage(message.getPayload()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


}
