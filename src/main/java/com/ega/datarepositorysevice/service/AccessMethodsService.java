package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.AccessMethods;
import reactor.core.publisher.Mono;

public interface AccessMethodsService {
    Mono<AccessMethods> getAccessMethodsById(Long objectId,Long id) ;

    Mono<AccessMethods> saveAccessMethod(Long objectId, Mono<AccessMethods> accessMethods);

    Mono<Void> deleteById(Long objectId, Long id) ;

    Mono<AccessMethods> updateAccessMethod(Long objectId,Mono<AccessMethods> accessMethodsMono) ;
}
