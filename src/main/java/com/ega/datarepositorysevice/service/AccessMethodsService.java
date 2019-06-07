package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Object;
import reactor.core.publisher.Mono;

public interface AccessMethodsService {
    Mono<AccessMethods> getAccessMethodsById(String id);
}
