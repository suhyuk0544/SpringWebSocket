package xyz.suhyuk0544.springwebsocket.WebSocket.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;
import xyz.suhyuk0544.springwebsocket.WebSocket.Connection.CustomConnectionManger;
import xyz.suhyuk0544.springwebsocket.WebSocket.Handlers.CustomWebSocketHandler;

import java.net.URI;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final CustomWebSocketHandler customWebSocketHandler;

//    private final ServerEndpointRegistration

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customWebSocketHandler,"/chat")
                .setAllowedOrigins("*");
    }


//    @Bean
//    public CustomConnectionManger customConnectionManger(){
//        return new CustomConnectionManger(,customWebSocketHandler,);
//    }

}
