package xyz.suhyuk0544.springwebsocket.Redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Session;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.adapter.StandardWebSocketSession;
import reactor.core.publisher.Sinks;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomRepository;
import xyz.suhyuk0544.springwebsocket.WebSocket.Dto.MessageDTO;

import java.io.Serial;
import java.io.Serializable;
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final RedisTemplate<String,Object> redisTemplate;

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        try{
            JSONObject jsonObject = new JSONObject(redisTemplate.getStringSerializer().deserialize(message.getBody()));
            String roomId = jsonObject.getString("roomId");
            log.info(roomId);
            messagingTemplate.convertAndSend("/sub/message/" + roomId, jsonObject.toString());
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}