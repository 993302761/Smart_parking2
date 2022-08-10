package com.example.configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient  //@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
@SpringBootApplication
public class ConfigClientApplication {

    //curl -X POST http://localhost:9998/actuator/refresh
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
