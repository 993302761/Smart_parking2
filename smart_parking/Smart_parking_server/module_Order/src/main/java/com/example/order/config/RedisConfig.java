package com.example.order.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        //设置key和HashKey 为String序列化
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        //设置Value和HashValue 为Json序列化
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }
}
