package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.service.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;

    @Autowired
    public BundleServiceImpl(BundleRepository bundleRepository) {
        this.bundleRepository = bundleRepository;
    }

    @Override
    public Mono<Bundle> getBundletById(Long id) {
        Optional<Bundle> bundleOpt = bundleRepository.findById(id);
        return bundleOpt.map(Mono::just).orElseGet(Mono::empty);
    }
}
