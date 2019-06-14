package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class ObjectServiceImpl implements ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    @Override
    public Mono<Object> getObjectById(Long id) {
        Optional<Object> objectOpt =  objectRepository.findById(id);
        return objectOpt.map(Mono::just).orElseGet(Mono::empty);
    }
}
