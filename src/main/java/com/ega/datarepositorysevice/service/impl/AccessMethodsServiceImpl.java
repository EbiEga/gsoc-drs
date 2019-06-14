package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AccessMethodsServiceImpl implements AccessMethodsService {

    @Autowired
    AccessMethodsRepository accessMethodsRepository;

    @Override
    public Mono<AccessMethods> getAccessMethodsById(Long id) {
        Optional<AccessMethods> accessMethodsOpt =  accessMethodsRepository.findById(id);
        return accessMethodsOpt.map(Mono::just).orElseGet(Mono::empty);
    }
}
