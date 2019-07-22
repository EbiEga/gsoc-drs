package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.Bundle;
import reactor.core.publisher.Mono;

public interface BundleService {
    Mono<Bundle> getBundleById(Long id);

    Mono<Void> deleteBundleById(Long id);

    Mono<Bundle> saveBundle(Mono<Bundle> bundleMono);

    Mono<Bundle> updateBundle(Mono<Bundle> bundleMono);
}
