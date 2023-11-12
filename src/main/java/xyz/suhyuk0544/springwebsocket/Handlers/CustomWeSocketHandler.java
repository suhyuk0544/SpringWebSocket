package xyz.suhyuk0544.springwebsocket.Handlers;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xyz.suhyuk0544.springwebsocket.Service.MessageService;
import xyz.suhyuk0544.springwebsocket.Service.MessageServiceImpl;

import java.util.HashMap;


@Component
public class CustomWeSocketHandler extends TextWebSocketHandler {

    private final HashMap<String,WebSocketSession> sessions = new HashMap<>();

    private final MessageService messageServiceImpl;


    public CustomWeSocketHandler(@Qualifier("messageServiceImpl") MessageService messageServiceImpl){
        this.messageServiceImpl = messageServiceImpl;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(),session);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {

        TextMessage textMessage = new TextMessage((CharSequence) message.getPayload());

        messageServiceImpl.

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        sessions.remove(session.getId());

        super.afterConnectionClosed(session, status);
    }
}
