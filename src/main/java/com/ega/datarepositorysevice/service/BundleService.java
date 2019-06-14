package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.Bundle;
import reactor.core.publisher.Mono;

public interface BundleService {
    Mono<Bundle> getBundletById(Long id);
}
