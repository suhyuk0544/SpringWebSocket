package xyz.suhyuk0544.springwebsocket.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import xyz.suhyuk0544.springwebsocket.WebSocket.Dto.MessageDTO;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic,MessageDTO message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}