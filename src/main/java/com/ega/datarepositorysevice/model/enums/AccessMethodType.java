package com.ega.datarepositorysevice.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum AccessMethodType {
    S3("s3"),
    GS("gs"),
    FTP("ftp"),
    GSIFTP("gsiftp"),
    GLOBUS("globus"),
    HTSGET("htsget"),
    HTTPS("https"),
    FILE("file");

    private String type;
    private static final Map<String, AccessMethodType> ENUM_MAP;


    AccessMethodType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public String getName() {
        return this.type;
    }

    static {
        Map<String, AccessMethodType> map = new ConcurrentHashMap<String, AccessMethodType>();
        for (AccessMethodType instance : AccessMethodType.values()) {
            map.put(instance.getName(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }


    @JsonCreator
    public static AccessMethodType createFromString(String name) {
        return ENUM_MAP.get(name.toLowerCase());
    }
}
