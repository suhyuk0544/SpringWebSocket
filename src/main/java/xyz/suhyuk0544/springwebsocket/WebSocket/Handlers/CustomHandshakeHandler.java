package xyz.suhyuk0544.springwebsocket.WebSocket.Handlers;

import jakarta.servlet.ServletContext;
import jakarta.websocket.Endpoint;
import jakarta.websocket.Extension;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.support.AbstractHandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import xyz.suhyuk0544.springwebsocket.WebSocket.Strategy.AbstractCustomUpgradeStrategy;

import java.util.List;


public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    public CustomHandshakeHandler(){
        super(new AbstractCustomUpgradeStrategy());
    }

}
