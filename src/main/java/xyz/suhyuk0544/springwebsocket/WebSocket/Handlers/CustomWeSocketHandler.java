package xyz.suhyuk0544.springwebsocket.WebSocket.Handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xyz.suhyuk0544.springwebsocket.WebSocket.Service.MessageService;
import xyz.suhyuk0544.springwebsocket.WebSocket.Service.MessageServiceImpl;

import java.util.HashMap;

@Slf4j
@Component
public class CustomWeSocketHandler extends TextWebSocketHandler {

    public static final HashMap<String,WebSocketSession> sessions = new HashMap<>();

    private final MessageServiceImpl messageServiceImpl;


    @Autowired
    public CustomWeSocketHandler(@Qualifier("messageServiceImpl") MessageService messageService){
        this.messageServiceImpl = (MessageServiceImpl) messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        sessions.put(session.getId(),session);

        messageServiceImpl.sendAllMemberMessage(session,new TextMessage("join:" + session.getId()));

        log.info(session.getId());

        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        log.info(message.getPayload());
        WebSocketSession socketSession = sessions.get(session.getId());

        if (socketSession != null && socketSession.isOpen())
            messageServiceImpl.sendAllMemberMessage(session,message);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        sessions.remove(session.getId());

        messageServiceImpl.sendAllMemberMessage(session,new TextMessage("out:"+ session.getId()));

        super.afterConnectionClosed(session, status);
    }
}
