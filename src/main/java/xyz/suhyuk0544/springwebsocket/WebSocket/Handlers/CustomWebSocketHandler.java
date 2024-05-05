package xyz.suhyuk0544.springwebsocket.WebSocket.Handlers;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.socket.WebSocketSession;
//import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomRepository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xyz.suhyuk0544.springwebsocket.Redis.RedisPublisher;
import xyz.suhyuk0544.springwebsocket.Redis.Room.Room;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomRepository;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomService;
import xyz.suhyuk0544.springwebsocket.WebSocket.Dto.MessageDTO;
import xyz.suhyuk0544.springwebsocket.WebSocket.Service.MessageService;
import xyz.suhyuk0544.springwebsocket.WebSocket.Service.MessageServiceImpl;
import xyz.suhyuk0544.springwebsocket.WebSocket.Session.CustomWebSocketSession;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomWebSocketHandler {


    private final MessageServiceImpl messageService;

//    private final RedisPublisher redisPublisher;
    private final RoomRepository roomRepository;
    private final RedisPublisher redisPublisher;
    private final RoomService roomService;

    @Autowired
    public CustomWebSocketHandler(@Qualifier("messageServiceImpl") MessageService messageService, RoomRepository roomRepository, RedisPublisher redisPublisher, RoomService roomService){
        this.messageService = (MessageServiceImpl) messageService;
        this.roomRepository = roomRepository;
        this.redisPublisher = redisPublisher;
        this.roomService = roomService;
    }

//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//        String roomId = Objects.requireNonNull(session.getHandshakeHeaders().get("roomId")).get(0);
//
//        log.info(session.getAttributes().toString());
//        log.info(session.toString());
//        log.info(roomId);
//
//        roomRepository.createChatRoom(roomId);
//        roomRepository.enterChatRoom(roomId);
//        log.info("room = {}",roomRepository.findRoomById(roomId).toString());
//
//        MessageDTO messageDTO = new MessageDTO();
//        messageDTO.setRoomId(roomId);
//        messageDTO.setMessage(session.getPrincipal().getName() + "님이 들어왔습니다");
//        messageService.sendMessage();

//        messageService.sendMessage(session,roomId,"님이 들어왔습니다");
//        redisPublisher.publish(roomRepository.getTopic(roomId),
//                 messageDTO);

//        super.afterConnectionEstablished(session);
//    }

//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//        JSONObject jsonObject = new JSONObject(message.getPayload());
//
//
//        WebSocketSession socketSession = sessions.get(session.getId());
//        log.info(session.toString());
//        log.info(session.getExtensions().toString());
//

//        if (socketSession != null && socketSession.isOpen())
//            messageService.sendAllMemberMessage(session,message);

//    }

//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//
//        sessions.remove(session.getId());
//
//        messageService.sendAllMemberMessage(session,new TextMessage("out"));

//        super.afterConnectionClosed(session, status);
//    }

    @MessageMapping(value = "/chat/message/{roomId}")
    public void message(MessageDTO message,@DestinationVariable String roomId,@AuthenticationPrincipal Principal principal) {
        message.setMessage(principal.getName() + ":" + message.getMessage());
//        roomRepository.enterChatRoom(roomId);
        redisPublisher.publish(roomRepository.getTopic(roomId), message);
    }

    @MessageMapping(value = "/chat/create")
    public void enter(MessageDTO message,@AuthenticationPrincipal Principal principal){
        message.setMessage(principal.getName() +"님이 채팅방에 참여하였습니다.");
        String roomId = message.getRoomId();

        roomRepository.createChatRoom(roomId);

        roomRepository.enterChatRoom(roomId);

        redisPublisher.publish(roomRepository.getTopic(roomId),message);
    }

    @MessageMapping(value = "/enter/wait")
    public void wait(@AuthenticationPrincipal Principal principal){
        MessageDTO messageDTO = new MessageDTO();
        String roomId = "";

        Room waitRoom = roomService.findRoomById("waitRoom")
                .map(room -> room.addUser(principal))
                .orElseThrow();
        

        log.info(waitRoom.getPrincipals().toString());

        if (waitRoom.getPrincipals().size() >= 2)
           roomId = roomService.createChatRoom(waitRoom);

        messageDTO.setRoomId(roomId);
        messageDTO.setMessage("createRoom");

        redisPublisher.publish(roomRepository.getTopic("waitRoom"),messageDTO);
    }

//    @MessageMapping(value = "/chat/message")
//    public void message(MessageDTO message){
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//    }

//    @MessageMapping(value = "/chat/out")
//    public void out(MessageDTO message, @AuthenticationPrincipal Principal principal) {
//        message.setMessage(principal.getName() + "님이 채팅방을 나갔습니다");
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(),message);
//    }

}
