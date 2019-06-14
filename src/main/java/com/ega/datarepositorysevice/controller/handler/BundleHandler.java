package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.service.BundleService;
import com.ega.datarepositorysevice.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class BundleHandler {
    private BundleService bundleService;

    @Autowired
    public BundleHandler(BundleService bundleService) {
        this.bundleService = bundleService;
    }

    public Mono<ServerResponse> getBundle(ServerRequest request){
        String id = request.pathVariable("bundle_id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Bundle> objectMono = bundleService.getBundletById(Long.parseLong(id));

        return objectMono.flatMap(user -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(user)))
                .switchIfEmpty(notFound);
    }
}
