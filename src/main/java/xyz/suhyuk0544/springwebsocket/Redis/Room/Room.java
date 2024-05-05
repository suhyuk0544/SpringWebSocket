package xyz.suhyuk0544.springwebsocket.Redis.Room;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serial;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    private final String id;

    private final ArrayList<Principal> principals;

    @TimeToLive
    private final long ttl;

    public Room addUser(Principal principal){
        principals.add(principal);
        return this;
    }


    public static RoomBuilder builder() {
        return new RoomBuilder();
    }

    @Override
    public String toString() {
        return "Room{" +
                "authId='" + id + '\'' +
//                ", name='" + name + '\'' +
                ", ttl=" + ttl +
                '}';
    }

    public static class RoomBuilder {
        private String id;
        private final ArrayList<Principal> principals = new ArrayList<>();
        private long ttl;

        public RoomBuilder User(final Principal principal) {
            this.principals.add(principal);
            return this;
        }

        public RoomBuilder id(final String id) {
            this.id = id;
            return this;
        }

        public RoomBuilder ttl(final long ttl) {
            this.ttl = ttl;
            return this;
        }

        public Room build() {
            return new Room(this.id,this.principals,this.ttl);
        }

        public String toString() {
            return "Room.RoomBuilder(name=" + this.id + ", ttl=" + this.ttl + ")";
        }
    }
}
