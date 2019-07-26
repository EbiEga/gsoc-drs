package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.controller.HandlerUtils;
import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import static com.ega.datarepositorysevice.controller.HandlerUtils.*;

@Component
public class AccessMethodHandler {
    private AccessMethodsService accessMethodsService;

    @Autowired
    public AccessMethodHandler(AccessMethodsService accessMethodsService) {
        this.accessMethodsService = accessMethodsService;
    }

    public Mono<ServerResponse> getAccess(ServerRequest request) {
        Mono<Tuple2<Long, Long>> parameters = Mono.zip(retrievePathVariable(request, OBJECT_PATH_VARIABLE), retrievePathVariable(request, ACCESS_METHODS_PATH_VARIABLE));
        return parameters.flatMap(tuple -> {
            Mono<AccessMethods> accessMethodsMono = accessMethodsService.getAccessMethodsById(tuple.getT1(), tuple.getT2());
            return accessMethodsMono.flatMap(HandlerUtils::returnOkResponse)
                    .onErrorResume(HandlerUtils::returnNotFound);
        }).onErrorResume(HandlerUtils::returnBadRequest);
    }

    public Mono<ServerResponse> saveAccess(ServerRequest request) {
        Mono<Long> monoLong = retrievePathVariable(request, OBJECT_PATH_VARIABLE);
        return monoLong.flatMap(parameter -> {
            Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(parameter, request.bodyToMono(AccessMethods.class));
            return accessMethodsMono.flatMap(HandlerUtils::returnOkResponse)
                    .onErrorResume(HandlerUtils::returnNotFound);
        })
                .onErrorResume(HandlerUtils::returnBadRequest);


    }

    public Mono<ServerResponse> deleteAccess(ServerRequest request) {
        Mono<Tuple2<Long, Long>> requestParameters = Mono.zip(retrievePathVariable(request, OBJECT_PATH_VARIABLE), retrievePathVariable(request, ACCESS_METHODS_PATH_VARIABLE));
        return requestParameters.flatMap(parameters -> {
            Mono<Void> accessMethods = accessMethodsService.deleteById(parameters.getT1(), parameters.getT2());
            return accessMethods.flatMap(HandlerUtils::returnOkResponse)
                    .onErrorResume(HandlerUtils::returnNotFound);
        })
                .onErrorResume(HandlerUtils::returnBadRequest);
    }

    public Mono<ServerResponse> updateAccess(ServerRequest request) {
        Mono<Tuple2<Long, Long>> requestParameters = Mono.zip(retrievePathVariable(request, OBJECT_PATH_VARIABLE), retrievePathVariable(request, ACCESS_METHODS_PATH_VARIABLE));
        return requestParameters.flatMap(parameters -> {
            Mono<AccessMethods> accessMethodsMono = accessMethodsService
                    .updateAccessMethod(parameters.getT1(), request.bodyToMono(AccessMethods.class));
            return accessMethodsMono.flatMap(HandlerUtils::returnOkResponse)
                    .onErrorResume(HandlerUtils::returnNotFound);
        })
                .onErrorResume(HandlerUtils::returnBadRequest);


    }
}


