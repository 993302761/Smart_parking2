package com.example.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    public static void main(String[] args) {
        //客户端全局广播配置  端口不可热部署
        //  curl -X POST "http://localhost:9999/actuator/bus-refresh"
        //客户端定点通知
        //  curl -X POST "http://localhost:9999/actuator/bus-refresh/application-name:port"

        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
