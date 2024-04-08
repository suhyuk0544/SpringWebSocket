package xyz.suhyuk0544.springwebsocket.WebSocket.Redis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.json.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "room")
public class Room {

    @Id
    private String authId;

    @Indexed
    private String name;

    private JSONArray sessions;

    @TimeToLive
    private long ttl;

//    public Room(String authId, String name, long ttl) {
//        this.authId = authId;
//        this.name = name;
//        this.ttl = ttl;
//    }

    public Room update(String token, long ttl) {
        this.name = token;
        this.ttl = ttl;
        return this;
    }
    public static RoomBuilder builder() {
        return new RoomBuilder();
    }

    @Override
    public String toString() {
        return "Room{" +
                "authId='" + authId + '\'' +
                ", name='" + name + '\'' +
                ", ttl=" + ttl +
                '}';
    }

    public static class RoomBuilder {
        private String authId;
        private String name;
        private JSONArray sessions = new JSONArray();
        private long ttl;

        public RoomBuilder authId(final String authId) {
            this.authId = authId;
            return this;
        }

        public RoomBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public RoomBuilder session(final CustomWebSocketSession session) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                this.sessions.put(objectMapper.writeValueAsString(session));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public RoomBuilder ttl(final long ttl) {
            this.ttl = ttl;
            return this;
        }

        public Room build() {
            return new Room(this.authId,this.name,this.sessions,this.ttl);
        }

        public String toString() {
            return "Room.RoomBuilder(authId=" + this.authId + ", name=" + this.name + ", ttl=" + this.ttl + ")";
        }
    }
}
