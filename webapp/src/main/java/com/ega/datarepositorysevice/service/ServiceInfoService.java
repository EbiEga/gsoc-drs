package com.ega.datarepositorysevice.service;

import reactor.core.publisher.Mono;
import webapp.ServiceInfo;

public interface ServiceInfoService {
    Mono<ServiceInfo> getServiceInfo();
}
