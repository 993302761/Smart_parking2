package com.example.parkingLots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.feign.api")
@EnableCircuitBreaker
//Hystrix开启
@EnableHystrix
public class ParkingLotsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotsApplication.class, args);
    }

}
