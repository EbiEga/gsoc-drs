package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.controller.HandlerUtils;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.service.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static com.ega.datarepositorysevice.controller.HandlerUtils.*;

@Component
public class BundleHandler {
    private BundleService bundleService;
    private Validator validator;
    @Autowired
    public BundleHandler(BundleService bundleService) {
        this.bundleService = bundleService;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public Mono<ServerResponse> getBundle(ServerRequest request) {
        Mono<Long> monoParameter =  retrievePathVariable(request, BUNDLE_PATH_VARIABLE);
        return monoParameter.flatMap(parameter->{
            Mono<Bundle> bundleMono = bundleService
                    .getBundleById(parameter);
            return bundleMono.flatMap(HandlerUtils::returnOkResponse)
                    .onErrorResume(HandlerUtils::returnNotFound);
        }).onErrorResume(HandlerUtils::returnBadRequest);

    }

    public Mono<ServerResponse> saveBundle(ServerRequest request) {
        Mono<Bundle> bundleMono = request.bodyToMono(Bundle.class);
        return bundleMono.flatMap(HandlerUtils::returnOkResponse)
                .onErrorResume(HandlerUtils::returnBadRequest)
                .switchIfEmpty(HandlerUtils.returnBadRequest(new IllegalArgumentException("Body is empty")));

    }

    public Mono<ServerResponse> deleteBundle(ServerRequest request) {
        Mono<Long> monoParameter = retrievePathVariable(request, BUNDLE_PATH_VARIABLE);
       return monoParameter
                .flatMap(parameter->{
                    Mono<Void> bundleMono = bundleService.deleteBundleById(parameter);
                    return bundleMono.flatMap(HandlerUtils::returnOkResponse)
                            .onErrorResume(HandlerUtils::returnNotFound);
                })
                .onErrorResume(HandlerUtils::returnBadRequest);
    }

    public Mono<ServerResponse> updateBundle(ServerRequest request) {
        Mono<Long> monoParameter = retrievePathVariable(request, BUNDLE_PATH_VARIABLE);
        return monoParameter
                .flatMap(parameter->{
                    Mono<Bundle> bundleMono = bundleService.updateBundle(request.bodyToMono(Bundle.class));
                    return bundleMono.flatMap(HandlerUtils::returnOkResponse)
                            .onErrorResume(HandlerUtils::returnNotFound);
                })
                .onErrorResume(HandlerUtils::returnBadRequest);
    }

}
