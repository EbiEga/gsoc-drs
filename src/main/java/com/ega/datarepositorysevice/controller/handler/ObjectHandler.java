package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.service.ObjectService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ObjectHandler {
    final static Logger logger = Logger.getLogger(ObjectHandler.class);

    private ObjectService objectService;

    @Autowired
    public ObjectHandler(ObjectService objectService) {
        this.objectService = objectService;
    }

    public Mono<ServerResponse> getObject(ServerRequest request) {
        String id = request.pathVariable("object_id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Object> objectMono = objectService.getObjectById(Long.parseLong(id));

        return objectMono.flatMap(user -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(user)))
                .switchIfEmpty(notFound);
    }
}
