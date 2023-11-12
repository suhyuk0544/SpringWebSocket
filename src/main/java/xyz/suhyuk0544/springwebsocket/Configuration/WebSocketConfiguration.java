package xyz.suhyuk0544.springwebsocket.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import xyz.suhyuk0544.springwebsocket.Handlers.CustomWeSocketHandler;
import xyz.suhyuk0544.springwebsocket.Service.MessageServiceImpl;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final CustomWeSocketHandler customWeSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customWeSocketHandler)
                .setAllowedOrigins("*");
    }

}
