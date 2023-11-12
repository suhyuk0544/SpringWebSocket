package xyz.suhyuk0544.springwebsocket.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.suhyuk0544.springwebsocket.Service.MessageServiceImpl;


@Configuration
public class SpringConfig {

    @Bean
    public MessageServiceImpl messageServiceImpl(){

        return new MessageServiceImpl();

    }


}
