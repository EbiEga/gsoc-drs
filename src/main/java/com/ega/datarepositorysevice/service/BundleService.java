package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.Bundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

public interface BundleService {
    Mono<Bundle> getBundleById(Long id);

    boolean deleteBundleById(Long id);

    Mono<Bundle> saveBundle(Mono<Bundle> bundleMono);

    Mono<Bundle> updateBundle(Mono<Bundle> bundleMono);
}
