package com.ega.datarepositorysevice.controller;

import com.ega.datarepositorysevice.controller.handler.ObjectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class ObjectRouter {

    @Autowired
    ObjectHandler objectHandler;

    @Bean
    RouterFunction<ServerResponse> route(){
        System.out.println("HERE");
        return RouterFunctions
                .route(GET("/objects/{object_id}").and(accept(APPLICATION_JSON)), objectHandler::getObject);
    }

}
