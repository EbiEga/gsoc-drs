package com.ega.datarepositorysevice.model.enums;

public enum AccessMethodType {
    S3 ("s3"),
    GS ("gs"),
    FTP ("ftp"),
    GSIFTP ("gsiftp"),
    GLOBUS ("globus"),
    HTSGET ("htsget"),
    HTTPS ("https"),
    FILE ("file");

    private String type;

    AccessMethodType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}
