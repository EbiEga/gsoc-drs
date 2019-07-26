package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.controller.HandlerUtils;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.service.ObjectService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Handler;

import static com.ega.datarepositorysevice.controller.HandlerUtils.*;

@Component
public class ObjectHandler {
    private ObjectService objectService;


    @Autowired
    public ObjectHandler(ObjectService objectService) {
        this.objectService = objectService;
    }

    public Mono<ServerResponse> getObject(ServerRequest request) {
        Mono<Long> monoParameter = retrievePathVariable(request, OBJECT_PATH_VARIABLE);
        return monoParameter
                .flatMap(parameter-> {

                    Mono<Object> objectMono = objectService.getObjectById(parameter);
                    return objectMono
                            .flatMap(HandlerUtils::returnOkResponse)
                            .onErrorResume(HandlerUtils::returnNotFound);
                })
                .onErrorResume(HandlerUtils::returnBadRequest);

    }

    public Mono<ServerResponse> saveObject(ServerRequest request) {
        Mono<Object> objectMono = request.bodyToMono(Object.class);
        return objectMono.flatMap(object -> {
            Mono<Object> savedObject = objectService.saveObject(Mono.just(object));
            return savedObject.flatMap(HandlerUtils::returnOkResponse)
                    .onErrorResume(HandlerUtils::returnBadRequest);
        }).onErrorResume(HandlerUtils::returnBadRequest);

    }

    public Mono<ServerResponse> deleteObject(ServerRequest request) {
            Mono<Long> monoParameter = retrievePathVariable(request, OBJECT_PATH_VARIABLE);
            return monoParameter
                    .flatMap(parameter->{
                        System.out.println(parameter);
                        Mono<Void> objectMono = objectService.deleteObjectById(parameter);
                        return objectMono
                                .then(HandlerUtils.returnOkResponse())
                                .onErrorResume(HandlerUtils::returnNotFound);
                    })
                    .onErrorResume(HandlerUtils::returnBadRequest);

    }

    public Mono<ServerResponse> updateObject(ServerRequest request) {
            Mono<Object> objectMono = objectService.updateObject(request.bodyToMono(Object.class));
            return objectMono
                    .flatMap(HandlerUtils::returnOkResponse)
                    .onErrorResume(HandlerUtils::returnNotFound);

    }
}
