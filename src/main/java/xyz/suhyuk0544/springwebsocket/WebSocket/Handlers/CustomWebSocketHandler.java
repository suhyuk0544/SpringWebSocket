package xyz.suhyuk0544.springwebsocket.WebSocket.Handlers;

import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xyz.suhyuk0544.springwebsocket.Redis.Room;
import xyz.suhyuk0544.springwebsocket.Redis.RoomRepository;
import xyz.suhyuk0544.springwebsocket.WebSocket.Service.MessageService;
import xyz.suhyuk0544.springwebsocket.WebSocket.Service.MessageServiceImpl;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    public static final HashMap<String,WebSocketSession> sessions = new HashMap<>();

    private final MessageServiceImpl messageService;

    private final RoomRepository roomRepository;

    @Autowired
    public CustomWebSocketHandler(@Qualifier("messageServiceImpl") MessageService messageService, RoomRepository roomRepository){
        this.messageService = (MessageServiceImpl) messageService;

        this.roomRepository = roomRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

//        StandardWebSocketSession standardWebSocketSession = session;
        String roomName = Objects.requireNonNull(session.getHandshakeHeaders().get("RoomName")).toString();

        String userName = Objects.requireNonNull(session.getPrincipal()).getName();
        log.info(session.getAttributes().toString());
        log.info(session.toString());
        log.info(session.getExtensions().toString());

        StandardWebSocketSession standardWebSocketSession = (StandardWebSocketSession) session;

        log.info(standardWebSocketSession.getId());

        Session session1 = (Session) standardWebSocketSession;

        log.info(session1.toString());

        //따로 서비스 계층으로 옮기기
        roomRepository.findByName(roomName).ifPresentOrElse(room ->
                room.getAuthId()
        ,() -> roomRepository.save(
             Room.builder()
                     .name(roomName)
                     .ttl(86400)
//                     .session((CustomWebSocketSession) standardWebSocketSession)
                        .build()));

        sessions.put(session.getId(),session);

        messageService.sendAllMemberMessage(session,new TextMessage("join"));

        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        WebSocketSession socketSession = sessions.get(session.getId());
        log.info(session.toString());
        log.info(session.getExtensions().toString());


        if (socketSession != null && socketSession.isOpen())
            messageService.sendAllMemberMessage(session,message);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        sessions.remove(session.getId());

        messageService.sendAllMemberMessage(session,new TextMessage("out"));

        super.afterConnectionClosed(session, status);
    }
}
