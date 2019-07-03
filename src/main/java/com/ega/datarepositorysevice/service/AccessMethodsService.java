package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.AccessMethods;
import reactor.core.publisher.Mono;

public interface AccessMethodsService {
    Mono<AccessMethods> getAccessMethodsById(Long id);
}
