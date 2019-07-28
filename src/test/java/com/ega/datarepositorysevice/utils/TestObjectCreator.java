package com.ega.datarepositorysevice.utils;

import com.ega.datarepositorysevice.controller.Router;
import com.ega.datarepositorysevice.controller.handler.AccessMethodHandler;
import com.ega.datarepositorysevice.controller.handler.BundleHandler;
import com.ega.datarepositorysevice.controller.handler.ObjectHandler;
import com.ega.datarepositorysevice.model.*;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.model.enums.BundleObjectType;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import com.ega.datarepositorysevice.service.BundleService;
import com.ega.datarepositorysevice.service.ObjectService;
import com.sun.org.apache.xml.internal.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

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
        return new AccessMethodHandler(accessMethodsService, objectService);
    }

    public static BundleHandler getBundleHandler(){
        return new BundleHandler(bundleService);
    }

    public static ObjectHandler getObjectHandler(){
        return new ObjectHandler(objectService);
    }


    public static AccessMethods getAccessMethods(){
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL(null,"https://www.youtube.com/watch?v=nsoIcQYlPxg", map);
        return new AccessMethods(null, AccessMethodType.S3, "region", accessURL);
    }

    public static Object getObject(){
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL(null,"https://www.youtube.com/watch?v=nsoIcQYlPxg", map);
        AccessMethods accessMethodsTestObject = new AccessMethods(null, AccessMethodType.S3, "region", accessURL);
        ArrayList<AccessMethods> accessMethodsArrayList = new ArrayList<>(Arrays.asList(accessMethodsTestObject));
        return new Object(null, "string", 0, date, date, "string", "application/json",
                Arrays.asList(new Checksum("s342ing", ChecksumType.MD5_Code)), accessMethodsArrayList, "string", null);
    }

    public static Bundle getBundle() throws URI.MalformedURIException {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        BundleObject bundleObject = new BundleObject(null,"string", BundleObjectType.OBJECT,Arrays.asList(new URI("https://asdasd.com")), null);
        return new Bundle(null, "string", 23, date,
                date, "string", Arrays.asList(new Checksum("string", ChecksumType.MD5_Code)
        ), "string", Arrays.asList("string"), Arrays.asList(bundleObject));
    }







}
