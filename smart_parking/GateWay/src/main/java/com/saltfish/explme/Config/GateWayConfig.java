package com.saltfish.explme.Config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/Administrators/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientAdministrators"))
                .route(p -> p
                        .path("/Order/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientOrder"))
                .route(p -> p
                        .path("/ParkingLots/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientParkingLots"))
                .route(p -> p
                        .path("/User/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientUser"))
                .route(p -> p
                        .path("/UserOrder/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientUser"))
                .route(p -> p
                        .path("/a/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientUser"))
                .route(p -> p
                        .path("/Vehicle/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientVehicle"))
                .build();
    }
}
