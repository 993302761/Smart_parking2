package com.feign.api.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;


public class FeignConfig {
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }

}
