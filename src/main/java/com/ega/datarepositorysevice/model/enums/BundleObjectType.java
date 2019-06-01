package com.ega.datarepositorysevice.model.enums;

public enum BundleObjectType {
    OBJECT ("object"),
    BUNDLE ("bundle");

    private String type;

    BundleObjectType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}

