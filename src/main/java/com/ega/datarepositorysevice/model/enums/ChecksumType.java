package com.ega.datarepositorysevice.model.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ChecksumType {

    MD5_Code("md5"),
    SHA256_CODE ("sha256"),
    ETAG_CODE ("etag"),
    SHA512_CODE ("sha512");

    private String type;
    private static final Map<String,ChecksumType> ENUM_MAP;


    ChecksumType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }

    static {
        Map<String,ChecksumType> map = new ConcurrentHashMap<String, ChecksumType>();
        for (ChecksumType instance : ChecksumType.values()) {
            map.put(instance.getName(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }


    public static ChecksumType createFromString(ChecksumType name) {
        return ENUM_MAP.get(name);
    }

    public String getName() {
        return this.type;
    }
}



