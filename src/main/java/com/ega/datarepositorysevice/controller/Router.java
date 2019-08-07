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
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
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
    Resource serviceInfo;

    @Autowired
    public Router(ObjectHandler objectHandler, BundleHandler bundleHandler, AccessMethodHandler accessMethodHandler) {
        this.objectHandler = objectHandler;
        this.bundleHandler = bundleHandler;
        this.accessMethodHandler = accessMethodHandler;
    }


    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .add(getRouter())
                .add(postRouter())
                .add(updateRouter())
                .add(deleteRouter())
                .add(staticRouter())
                .build();

    }

    private RouterFunction<ServerResponse> getRouter() {
        return RouterFunctions
                .route(GET("/objects/{object_id}").and(accept(APPLICATION_JSON)), objectHandler::getObject)
                .andRoute(GET("/bundles/{bundle_id}").and(accept(APPLICATION_JSON)), bundleHandler::getBundle)
                .andRoute(GET("/objects/{object_id}/access/{access_id}").and(accept(APPLICATION_JSON)), accessMethodHandler::getAccess);
    }

    private RouterFunction<ServerResponse> postRouter() {
        return RouterFunctions
                .route(POST("/objects").and(accept(APPLICATION_JSON)), objectHandler::saveObject)
                .andRoute(POST("/bundles").and(accept(APPLICATION_JSON)), bundleHandler::saveBundle)
                .andRoute(POST("/objects/{object_id}/access").and(accept(APPLICATION_JSON)), accessMethodHandler::saveAccess);
    }

    private RouterFunction<ServerResponse> updateRouter() {
        return RouterFunctions
                .route(PUT("/objects/{object_id}").and(accept(APPLICATION_JSON)), objectHandler::updateObject)
                .andRoute(PUT("/bundles/{bundle_id}").and(accept(APPLICATION_JSON)), bundleHandler::updateBundle)
                .andRoute(PUT("/objects/{object_id}/access/{access_id}").and(accept(APPLICATION_JSON)), accessMethodHandler::updateAccess);
    }

    private RouterFunction<ServerResponse> deleteRouter() {
        return RouterFunctions
                .route(DELETE("/objects/{object_id}").and(accept(APPLICATION_JSON)), objectHandler::deleteObject)
                .andRoute(DELETE("/bundles/{bundle_id}").and(accept(APPLICATION_JSON)), bundleHandler::deleteBundle)
                .andRoute(DELETE("/objects/{object_id}/access/{access_id}").and(accept(APPLICATION_JSON)), accessMethodHandler::deleteAccess);
    }

    private RouterFunction<ServerResponse> staticRouter() {
        return RouterFunctions.route(GET("/service-info"), request ->
                ok().contentType(MediaType.APPLICATION_JSON).syncBody(serviceInfo)

        );
    }

}
