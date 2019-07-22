package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.Object;
import reactor.core.publisher.Mono;

public interface ObjectService {
    Mono<Object> getObjectById(Long id);

    Mono<Void> deleteObjectById(Long id);

    Mono<Object> saveObject(Mono<Object> objectMono);

    Mono<Object> updateObject(Mono<Object> objectMono);
}
