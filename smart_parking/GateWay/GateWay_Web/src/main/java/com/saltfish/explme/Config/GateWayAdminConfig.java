package com.saltfish.explme.Config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayAdminConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/Administrators/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientAdministrators"))
                .route(p -> p
                        .path("//ParkingLots/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientParkingLots"))
                .build();
    }
}
