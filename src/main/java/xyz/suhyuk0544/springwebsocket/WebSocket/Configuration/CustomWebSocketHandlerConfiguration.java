package xyz.suhyuk0544.springwebsocket.WebSocket.Configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.ServletWebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.sockjs.SockJsService;

@Configuration
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomWebSocketHandlerConfiguration {

    private final ServletWebSocketHandlerRegistration servletWebSocketHandlerRegistration;

    @Bean
    public void setWebSocketHandler(){
        servletWebSocketHandlerRegistration.setHandshakeHandler(new DefaultHandshakeHandler());
    }

}
