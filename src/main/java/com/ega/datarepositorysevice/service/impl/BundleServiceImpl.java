package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.service.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;

    @Autowired
    public BundleServiceImpl(BundleRepository bundleRepository) {
        this.bundleRepository = bundleRepository;
    }

    @Override
    public Mono<Bundle> getBundleById(Long id) {
        Optional<Bundle> bundleOpt = bundleRepository.findById(id);
        return bundleOpt.map(Mono::just).orElseGet(Mono::empty);
    }
}
