package xyz.suhyuk0544.springwebsocket.Redis.Room;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;
import xyz.suhyuk0544.springwebsocket.Redis.RedisSubscriber;
import xyz.suhyuk0544.springwebsocket.Redis.Room.Room;

import java.util.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Repository
public class RoomRepository {


    private final RedisMessageListenerContainer redisMessageListener;

    private final RedisSubscriber redisSubscriber;
    private static final String CHAT_ROOMS = "CHAT_ROOM";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Room> opsHashChatRoom;
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
        createChatRoom("waitRoom");
    }

    public List<Room> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public Optional<Room> findRoomById(String id) {
        return Optional.of(opsHashChatRoom.get(CHAT_ROOMS, id));
    }

    public Room createChatRoom(String roomId) {
        Room room = Room.builder()
                .id(roomId)
                .build();
        opsHashChatRoom.put(CHAT_ROOMS,room.getId() , room);
        return room;
    }

    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

}