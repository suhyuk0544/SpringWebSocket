package xyz.suhyuk0544.springwebsocket.WebSocket.Configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;
import xyz.suhyuk0544.springwebsocket.WebSocket.Connection.CustomConnectionManger;
import xyz.suhyuk0544.springwebsocket.WebSocket.Handlers.CustomHandshakeHandler;
import xyz.suhyuk0544.springwebsocket.WebSocket.Handlers.CustomWebSocketHandler;

import java.net.URI;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
//                .setHandshakeHandler(new CustomHandshakeHandler())
                .setAllowedOrigins("*");
    }
//
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub")
                .enableSimpleBroker("/sub");
    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(customWebSocketHandler,"/chat")
//                .setAllowedOrigins("*")
//                .setHandshakeHandler(new CustomHandshakeHandler());
//    }
}
