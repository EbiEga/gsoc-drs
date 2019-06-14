package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.service.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class BundleServiceImpl implements BundleService {

    @Autowired
    BundleRepository bundleRepository;

    @Override
    public Mono<Bundle> getBundletById(Long id) {
        Optional<Bundle> bundleOpt =  bundleRepository.findById(id);
        return bundleOpt.map(Mono::just).orElseGet(Mono::empty);
    }
}
