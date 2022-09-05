package com.example.Integral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableCircuitBreaker
//Hystrix开启
@EnableHystrix
public class IntegralApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegralApplication.class);
    }
}
