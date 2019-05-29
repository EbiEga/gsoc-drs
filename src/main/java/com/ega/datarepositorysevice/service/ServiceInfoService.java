package com.ega.datarepositorysevice.service;

import com.ega.datarepositorysevice.model.ServiceInfo;
import reactor.core.publisher.Mono;

public interface ServiceInfoService {
    Mono<ServiceInfo> getServiceInfo();
}
