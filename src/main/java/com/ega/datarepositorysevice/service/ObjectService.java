package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.Object;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

public interface ObjectService {
    Mono<Object> getObjectById(Long id);

    boolean deleteObjectById(Long id);

    Mono<Object> saveObject(Mono<Object> objectMono);

    Mono<Object> updateObject(Mono<Object> objectMono);
}
