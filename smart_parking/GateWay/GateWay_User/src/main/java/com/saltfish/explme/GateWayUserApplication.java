package com.saltfish.explme;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GateWayUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayUserApplication.class,args);
    }
}
