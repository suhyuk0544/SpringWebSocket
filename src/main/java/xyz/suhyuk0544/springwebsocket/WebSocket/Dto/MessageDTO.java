package xyz.suhyuk0544.springwebsocket.WebSocket.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

    private String roomId;
    private String message;

    @Override
    public String toString() {
        return "MessageDTO{" +
                "roomId='" + roomId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
