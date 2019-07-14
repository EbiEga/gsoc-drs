package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.controller.HandlerUtils;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.service.ObjectService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.ega.datarepositorysevice.controller.HandlerUtils.OBJECT_PATH_VARIABLE;
import static com.ega.datarepositorysevice.controller.HandlerUtils.retrievePathVariable;

@Component
public class ObjectHandler {
    private ObjectService objectService;

    @Autowired
    public ObjectHandler(ObjectService objectService) {
        this.objectService = objectService;
    }

    public Mono<ServerResponse> getObject(ServerRequest request) {

        try {
            Error notFoundError = new Error("The requested Object wasn't found", HttpStatus.NOT_FOUND);
            Mono<Object> objectMono = objectService
                    .getObjectById(retrievePathVariable(request, OBJECT_PATH_VARIABLE));
            return HandlerUtils.returnOkResponse(objectMono, notFoundError);
        } catch (IllegalArgumentException e) {
            return HandlerUtils.returnBadRequest(e);
        }

    }

    public Mono<ServerResponse> saveObject(ServerRequest request) {
        Mono<Object> objectMono = objectService.saveObject(request.bodyToMono(Object.class));
        return HandlerUtils
                .returnOkResponse(objectMono);
    }

    public Mono<ServerResponse> deleteObject(ServerRequest request) {
        if(objectService.deleteObjectById(retrievePathVariable(request,OBJECT_PATH_VARIABLE))){
            return HandlerUtils.returnOkResponse();
        }else{
            return HandlerUtils.returnBadRequest(new IllegalArgumentException("The Object is not found"));

        }
    }

    public Mono<ServerResponse> updateObject(ServerRequest request) {
        try {
            Error notFoundError =  new Error("The requested Object wasn't found", HttpStatus.NOT_FOUND);
            Mono<Object> objectMono = objectService.updateObject(request.bodyToMono(Object.class));
            return HandlerUtils.returnOkResponse(objectMono, notFoundError);
        }catch (IllegalArgumentException e){
            return HandlerUtils.returnBadRequest(e);
        }
    }
}
