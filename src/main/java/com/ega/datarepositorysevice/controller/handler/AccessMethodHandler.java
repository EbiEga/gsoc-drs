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

    public Mono<ServerResponse> saveAccess(ServerRequest request) {
        Mono<AccessMethods> accessMethodsMono = accessMethodsService.saveAccessMethod(request.bodyToMono(AccessMethods.class));

        return HandlerUtils
                .returnOkResponse(accessMethodsMono);

    }

    public Mono<ServerResponse> deleteAccess(ServerRequest request) {
        if (accessMethodsService.deleteById(retrievePathVariable(request, ACCESS_METHODS_PATH_VARIABLE))){
            return HandlerUtils.returnOkResponse();
        }else {
            return HandlerUtils.returnBadRequest(new IllegalArgumentException("The Access is not found"));
        }
    }

    public Mono<ServerResponse> updateAccess(ServerRequest request) {
        try {
            Error notFoundError = new Error("The requested AccessMethod wasn't found", HttpStatus.NOT_FOUND);

            Mono<AccessMethods> accessMethodsMono = accessMethodsService
                    .updateAccessMethod(request.bodyToMono(AccessMethods.class));
            return HandlerUtils.returnOkResponse(accessMethodsMono, notFoundError);
        }catch (IllegalArgumentException e){
            return HandlerUtils.returnBadRequest(e);
        }
    }

}
