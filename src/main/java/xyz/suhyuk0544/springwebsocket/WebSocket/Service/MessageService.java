package xyz.suhyuk0544.springwebsocket.WebSocket.Service;


import org.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

public interface MessageService {

    void sendMessage(WebSocketSession session, String message,String id);

}
