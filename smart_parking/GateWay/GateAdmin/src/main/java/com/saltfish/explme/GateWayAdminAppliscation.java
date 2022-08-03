package com.saltfish.explme;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

@EnableEurekaClient
@SpringBootApplication
public class GateWayAdminAppliscation {
    public static void main(String[] args) {
        SpringApplication.run(GateWayAdminAppliscation.class,args);
    }
}
