package xyz.suhyuk0544.springwebsocket.WebSocket.Connection;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.ConnectionManagerSupport;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import java.net.URI;

public class CustomConnectionManger extends WebSocketConnectionManager {
    public CustomConnectionManger(WebSocketClient client, WebSocketHandler webSocketHandler, String uriTemplate, Object... uriVariables) {
        super(client, webSocketHandler, uriTemplate, uriVariables);
    }

    public CustomConnectionManger(WebSocketClient client, WebSocketHandler webSocketHandler, URI uri) {
        super(client, webSocketHandler, uri);
    }


    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    protected void openConnection() {

    }

    @Override
    protected void closeConnection() throws Exception {

    }
}
