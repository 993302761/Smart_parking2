package com.example.user.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    //  RabbitMQ会对发送对象做序列化，spring对消息对象的处理是由MessageConverter处理,
    //  默认实现的是SimpleMessageConverter，基于JDK的ObjectOutPutStream完成序列化
    //  若要修改，只需要定义一个MessageConverter类型的Bean就好
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
