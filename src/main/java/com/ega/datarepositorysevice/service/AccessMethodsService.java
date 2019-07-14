package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.AccessMethods;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

public interface AccessMethodsService {
    Mono<AccessMethods> getAccessMethodsById(Long id);

    Mono<AccessMethods> saveAccessMethod(Mono<AccessMethods> accessMethods);

    boolean deleteById(Long id);

    Mono<AccessMethods> updateAccessMethod(Mono<AccessMethods> accessMethodsMono);
}
