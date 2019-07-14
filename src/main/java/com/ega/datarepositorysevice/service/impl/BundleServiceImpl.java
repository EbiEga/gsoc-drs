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

    @Override
    public boolean deleteBundleById(Long id) {
        try {
            bundleRepository.deleteById(id);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public Mono<Bundle> saveBundle(Mono<Bundle> bundleMono) {
        return bundleMono.map(bundleRepository::save);
    }

    @Override
    public Mono<Bundle> updateBundle(Mono<Bundle> bundleMono) {
        return bundleMono.map(bundle -> {
            if(bundleRepository.existsById(bundle.getId())){
                return bundleRepository.save(bundle);
            }else {
                throw new IllegalArgumentException("id of bundle is not found");
            }
        });
    }
}
