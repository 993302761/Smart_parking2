package com.example.parkingLots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;

@SpringBootApplication
@EnableFeignClients
public class ParkingLotsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotsApplication.class, args);
    }

}
