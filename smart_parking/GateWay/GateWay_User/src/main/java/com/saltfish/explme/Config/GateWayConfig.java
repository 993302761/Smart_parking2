package com.saltfish.explme.Config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        //localhost:网关端口号/你的服务请求
        return builder.routes()
                .route(p -> p
                        .path("/User/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://ClientUser"))
                .build();
    }
}
