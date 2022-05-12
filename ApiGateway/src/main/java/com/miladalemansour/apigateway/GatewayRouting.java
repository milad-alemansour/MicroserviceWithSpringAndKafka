package com.miladalemansour.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouting {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("apiId", r -> r.path("/api/**").
                        uri("lb://API-SERVICE"))
                .route("journalId",r -> r.path("/journal/**").uri("lb://JOURNAL-SERVICE"))
                .route("callServiceId", r-> r.path("/call/**").uri("lb://CALL-SERVICE"))
                .build();


    }
}
