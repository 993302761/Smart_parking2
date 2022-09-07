package com.saltfish.explme;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GateWayWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayWebApplication.class,args);
    }
}
