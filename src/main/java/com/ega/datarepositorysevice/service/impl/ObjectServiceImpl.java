package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ObjectServiceImpl implements ObjectService {

    private final ObjectRepository objectRepository;

    @Autowired
    public ObjectServiceImpl(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @Override
    public Mono<Object> getObjectById(Long id) {
        Optional<Object> objectOpt =  objectRepository.findById(id);
        return objectOpt.map(Mono::just).orElseGet(Mono::empty);
    }
}
