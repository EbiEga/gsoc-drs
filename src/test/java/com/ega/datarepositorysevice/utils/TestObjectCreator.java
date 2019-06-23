package com.ega.datarepositorysevice.utils;

import com.ega.datarepositorysevice.controller.Router;
import com.ega.datarepositorysevice.controller.handler.AccessMethodHandler;
import com.ega.datarepositorysevice.controller.handler.BundleHandler;
import com.ega.datarepositorysevice.controller.handler.ObjectHandler;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import com.ega.datarepositorysevice.service.BundleService;
import com.ega.datarepositorysevice.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerResponse;

public class TestObjectCreator {

    @Autowired
    public static AccessMethodsService accessMethodsService;

    @Autowired
    public static BundleService bundleService;

    @Autowired
    public static ObjectService objectService;



    public static Router getRouter(){
        return new Router(getObjectHandler(),getBundleHandler(), getAccessMethodHandler());
    }

    public static AccessMethodHandler getAccessMethodHandler(){
        return new AccessMethodHandler(accessMethodsService);
    }

    public static BundleHandler getBundleHandler(){
        return new BundleHandler(bundleService);
    }

    public static ObjectHandler getObjectHandler(){
        return new ObjectHandler(objectService);
    }






}
