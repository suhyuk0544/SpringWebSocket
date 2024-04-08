package xyz.suhyuk0544.springwebsocket.WebSocket.Redis.Serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class WebSocketSessionSerializer implements RedisSerializer<WebSocketSessionSerializer> {



    @Override
    public byte[] serialize(WebSocketSessionSerializer value) throws SerializationException {
        return new byte[0];
    }

    @Override
    public WebSocketSessionSerializer deserialize(byte[] bytes) throws SerializationException {
        return null;
    }

    @Override
    public boolean canSerialize(Class<?> type) {
        return RedisSerializer.super.canSerialize(type);
    }

    @Override
    public Class<?> getTargetType() {
        return RedisSerializer.super.getTargetType();
    }

}
