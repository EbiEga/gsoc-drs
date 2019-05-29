package com.ega.datarepositorysevice.controller;

import com.ega.datarepositorysevice.model.ServiceInfo;
import com.ega.datarepositorysevice.service.ServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/service-info")
public class ServiceInfoController {

    private final ServiceInfoService serviceInfo;


    @Autowired
    public ServiceInfoController(ServiceInfoService serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ServiceInfo> getServiceInfo(){
        return serviceInfo.getServiceInfo();
    }
}
