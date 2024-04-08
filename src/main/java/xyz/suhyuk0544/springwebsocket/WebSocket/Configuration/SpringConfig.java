package xyz.suhyuk0544.springwebsocket.WebSocket.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.suhyuk0544.springwebsocket.WebSocket.Connection.CustomConnectionManger;
import xyz.suhyuk0544.springwebsocket.WebSocket.Handlers.CustomWebSocketHandler;
import xyz.suhyuk0544.springwebsocket.WebSocket.Service.MessageServiceImpl;


@Configuration
public class SpringConfig {

    @Bean
//    @Qualifier("messageServiceImpl")
    public MessageServiceImpl messageServiceImpl(){

        return new MessageServiceImpl();

    }



}
