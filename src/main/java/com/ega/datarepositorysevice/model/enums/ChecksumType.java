package com.ega.datarepositorysevice.model.enums;

public enum ChecksumType {

    MD5_Code("md5"),
    SHA256_CODE ("sha256"),
    ETAG_CODE ("etag"),
    SHA512_CODE ("sha512");

    private String type;

    ChecksumType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}


}
