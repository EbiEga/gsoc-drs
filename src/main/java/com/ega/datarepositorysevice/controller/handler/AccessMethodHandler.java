package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class AccessMethodHandler {
    private AccessMethodsService accessMethodsService;

    @Autowired
    public AccessMethodHandler(AccessMethodsService objectService) {
        this.accessMethodsService = objectService;
    }

    public Mono<ServerResponse> getObject(ServerRequest request){
        String id = request.pathVariable("access_id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<AccessMethods> objectMono = accessMethodsService.getAccessMethodsById(Long.parseLong(id));

        return objectMono.flatMap(user -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(user)))
                .switchIfEmpty(notFound);
    }
}
