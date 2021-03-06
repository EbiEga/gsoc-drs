package com.ega.datarepositorysevice.controller;

import com.ega.datarepositorysevice.controller.handler.AccessMethodHandler;
import com.ega.datarepositorysevice.controller.handler.BundleHandler;
import com.ega.datarepositorysevice.controller.handler.ObjectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class Router {

    private final
    ObjectHandler objectHandler;

    private final
    BundleHandler bundleHandler;

    private final
    AccessMethodHandler accessMethodHandler;

    @Value("classpath:/static/service-info.json")
    private Resource serviceInfo;

    @Autowired
    public Router(ObjectHandler objectHandler, BundleHandler bundleHandler, AccessMethodHandler accessMethodHandler) {
        this.objectHandler = objectHandler;
        this.bundleHandler = bundleHandler;
        this.accessMethodHandler = accessMethodHandler;
    }


    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(GET("/objects/{object_id}").and(accept(APPLICATION_JSON)), objectHandler::getObject)
                .andRoute(GET("/bundles/{bundle_id}").and(accept(APPLICATION_JSON)), bundleHandler::getBundle)
                .andRoute(GET("/objects/{object_id}/access/{access_id}").and(accept(APPLICATION_JSON)), accessMethodHandler::getAccess)
                .and(staticRouter());

    }


    public RouterFunction<ServerResponse> staticRouter() {
        return RouterFunctions.route(GET("/service-info"), request ->
                ok().contentType(MediaType.APPLICATION_JSON).syncBody(serviceInfo)

        );
    }


}
