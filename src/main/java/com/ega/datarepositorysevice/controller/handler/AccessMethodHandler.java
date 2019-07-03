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

import static com.ega.datarepositorysevice.controller.HandlerUtils.ACCESS_METHODS_PATH_VARIABLE;
import static com.ega.datarepositorysevice.controller.HandlerUtils.retrievePathVariable;

@Component
public class AccessMethodHandler {
    private AccessMethodsService accessMethodsService;

    @Autowired
    public AccessMethodHandler(AccessMethodsService accessMethodsService) {
        this.accessMethodsService = accessMethodsService;
    }

    public Mono<ServerResponse> getAccess(ServerRequest request) {
        try {
            Error notFoundError = new Error("The requested AccessMethod wasn't found", HttpStatus.NOT_FOUND);
            Mono<AccessMethods> accessMethodMono = accessMethodsService
                    .getAccessMethodsById(retrievePathVariable(request, ACCESS_METHODS_PATH_VARIABLE));
            return HandlerUtils.returnOkResponse(accessMethodMono, notFoundError);
        } catch (IllegalArgumentException e) {
            return HandlerUtils.returnBadRequest(e);
        }


    }
}
