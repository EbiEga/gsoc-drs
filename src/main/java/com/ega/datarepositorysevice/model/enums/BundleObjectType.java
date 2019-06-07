package com.ega.datarepositorysevice.model.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum BundleObjectType {
    OBJECT ("object"),
    BUNDLE ("bundle");

    private String type;
    private static final Map<String,BundleObjectType> ENUM_MAP;


    BundleObjectType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }


    static {
        Map<String,BundleObjectType> map = new ConcurrentHashMap<String, BundleObjectType>();
        for (BundleObjectType instance : BundleObjectType.values()) {
            map.put(instance.getName(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }


    public static BundleObjectType createFromString(String name) {
        return ENUM_MAP.get(name);
    }

    public String getName() {
        return this.type;
    }
}

