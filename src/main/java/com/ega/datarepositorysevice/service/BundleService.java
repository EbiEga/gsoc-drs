package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Object;
import reactor.core.publisher.Mono;

public interface BundleService {
    Mono<Bundle> getBundletById(String id);
}
