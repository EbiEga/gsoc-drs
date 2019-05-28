package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.service.ServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import webapp.ServiceInfo;
import webapp.ServiceInfoRepository;

@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {

    private final ServiceInfoRepository infoRepository;

    ServiceInfoServiceImpl(ServiceInfoRepository infoRepository){
        this.infoRepository = infoRepository;
    }

    @Autowired
    ServiceInfoRepository serviceInfoRepository;

    @Override
    public Mono<ServiceInfo> getServiceInfo() {
        ServiceInfo info = serviceInfoRepository.findDistinctFirstByVersion();
        return Mono.just(info);
    }
}
