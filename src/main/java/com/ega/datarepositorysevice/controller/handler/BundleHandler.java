package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.controller.HandlerUtils;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.service.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.ega.datarepositorysevice.controller.HandlerUtils.BUNDLE_PATH_VARIABLE;
import static com.ega.datarepositorysevice.controller.HandlerUtils.retrievePathVariable;

@Component
public class BundleHandler {
    private BundleService bundleService;

    @Autowired
    public BundleHandler(BundleService bundleService) {
        this.bundleService = bundleService;
    }

    public Mono<ServerResponse> getBundle(ServerRequest request) {
        try {
            Error notFoundError = new Error("The requested Bundle wasn't found", HttpStatus.NOT_FOUND);;
            Mono<Bundle> bundleMono = bundleService
                    .getBundletById(retrievePathVariable(request,BUNDLE_PATH_VARIABLE));
            return HandlerUtils.returnOkResponse(bundleMono, notFoundError);
        } catch (IllegalArgumentException e){
            return HandlerUtils.returnBadRequest(e);
        }

    }
}
