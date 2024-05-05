package xyz.suhyuk0544.springwebsocket.WebSocket.Service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import xyz.suhyuk0544.springwebsocket.Redis.RedisPublisher;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomRepository;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final RedisPublisher redisPublisher;

    private final RoomRepository roomRepository;


    @Override
    public void sendMessage(WebSocketSession session,String id,String message) {

//         redisPublisher.publish(roomRepository.getTopic(message),
//                 Objects.requireNonNull(session.getPrincipal()).getName() + message);


//        sessions.values().forEach(s -> {
//            if (!session.getId().equals(s.getId())) {
//                try {
//                    s.sendMessage(new TextMessage(session.getPrincipal().getName() + ":" + message.getPayload()));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
    }
}