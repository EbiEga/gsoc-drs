package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.Bundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface BundleService {
    Mono<Bundle> getBundletById(Long id);
}
