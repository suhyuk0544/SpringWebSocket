package xyz.suhyuk0544.springwebsocket.WebSocket.Redis;

import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import io.lettuce.core.dynamic.RedisCommandFactory;


import jakarta.persistence.criteria.CriteriaBuilder;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.serializer.Serializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.*;
import org.springframework.web.reactive.socket.adapter.StandardWebSocketSession;

import java.util.ArrayList;

@Configuration
public class RedisConfiguration {

//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private Integer port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){

        //        System.out.println(host);;

//        return new LettuceConnectionFactory(host,port);
        return new LettuceConnectionFactory("localhost",6379);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(""));
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new );


        return redisTemplate;
    }


}
