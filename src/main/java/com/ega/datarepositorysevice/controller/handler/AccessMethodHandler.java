package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.controller.HandlerUtils;
import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import com.ega.datarepositorysevice.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static com.ega.datarepositorysevice.controller.HandlerUtils.*;

@Component
public class AccessMethodHandler {
    private AccessMethodsService accessMethodsService;
    private ObjectService objectService;

    private Validator validator;

    @Autowired
    public AccessMethodHandler(AccessMethodsService accessMethodsService, ObjectService objectService) {
        this.accessMethodsService = accessMethodsService;
        this.objectService = objectService;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
        return monoLong
                .flatMap(parameter -> {
                    Mono<AccessMethods> bodyMono = request.bodyToMono(AccessMethods.class);

                    Mono<AccessMethods> accessMethodsMono = bodyMono.flatMap(body -> {
                        Set<ConstraintViolation<AccessMethods>> constraintViolations = validator.validate(body);
                        if (!constraintViolations.isEmpty()) {
                            return Mono.error(new IllegalArgumentException(HandlerUtils.generateErrorMessageFromConstraints(constraintViolations)));
                        } else {
                            return accessMethodsService.saveAccessMethod(parameter, Mono.just(body));
                        }
                    }).switchIfEmpty(Mono.empty());

                    return accessMethodsMono.flatMap(HandlerUtils::returnCreatedResponse)
                            .onErrorResume(HandlerUtils::handleError)
                            .switchIfEmpty(HandlerUtils.returnBadRequest(new IllegalArgumentException("Request body is empty")));

                })
                .onErrorResume(HandlerUtils::returnBadRequest);


    }

    public Mono<ServerResponse> deleteAccess(ServerRequest request) {
        Mono<Tuple2<Long, Long>> requestParameters = Mono.zip(retrievePathVariable(request, OBJECT_PATH_VARIABLE), retrievePathVariable(request, ACCESS_METHODS_PATH_VARIABLE));
        return requestParameters.flatMap(parameters -> {
            Long objectId = parameters.getT1();
            Long accessId = parameters.getT2();
            Mono<Object> objectMono = objectService.getObjectById(objectId);
            Object object = objectMono.block();
            if (object.getAccessMethods().size() <= 1) {
                return HandlerUtils.returnBadRequest(new IllegalArgumentException("AccessMethods list of given object can't be empty"));

            }
            boolean deleted = object.deleteAccessMethod(accessId);
            if (!deleted) {
                return HandlerUtils.returnNotFound(new IllegalArgumentException("id not found"));
            }
            Mono<Object> updateObject = objectService.updateObject(Mono.just(object));
            return updateObject
                    .then(HandlerUtils.returnOkResponse())
                    .onErrorResume(HandlerUtils::returnNotFound);
        })
                .onErrorResume(HandlerUtils::handleError);
    }

    public Mono<ServerResponse> updateAccess(ServerRequest request) {
        Mono<Tuple2<Long, Long>> requestParameters = Mono.zip(retrievePathVariable(request, OBJECT_PATH_VARIABLE), retrievePathVariable(request, ACCESS_METHODS_PATH_VARIABLE));
        return requestParameters.flatMap(parameters -> {
            Mono<AccessMethods> bodyMono = request.bodyToMono(AccessMethods.class);
            Mono<AccessMethods> accessMethodsMono = bodyMono.flatMap(body -> {
                Set<ConstraintViolation<AccessMethods>> constraintViolations = validator.validate(body);
                if (!constraintViolations.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(HandlerUtils.generateErrorMessageFromConstraints(constraintViolations)));
                }
                body.setAccessId(parameters.getT2());
                return accessMethodsService
                        .updateAccessMethod(parameters.getT1(), Mono.just(body));
            })
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Request body is empty")));

            return accessMethodsMono.flatMap(HandlerUtils::returnOkResponse)
                    .onErrorResume(HandlerUtils::handleError);
        })
                .onErrorResume(HandlerUtils::handleError);


    }
}


