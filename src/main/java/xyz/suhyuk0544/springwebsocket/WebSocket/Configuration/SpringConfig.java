package xyz.suhyuk0544.springwebsocket.WebSocket.Configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.ServletWebSocketHandlerRegistration;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomRepository;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomService;
import xyz.suhyuk0544.springwebsocket.WebSocket.Handlers.Adapter.CustomWebSocketHandlerAdapter;
import xyz.suhyuk0544.springwebsocket.WebSocket.Service.MessageServiceImpl;


@Configuration
public class SpringConfig {


    @Bean
    public ServletWebSocketHandlerRegistration servletWebSocketHandlerRegistration(){
        return new ServletWebSocketHandlerRegistration();
    }

//    @Bean
//    public CustomWebSocketHandlerAdapter customWebSocketHandlerAdapter() {
//        return new CustomWebSocketHandlerAdapter()
//    }

}
