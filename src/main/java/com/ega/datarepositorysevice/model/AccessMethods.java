package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "access_methods")
@JsonInclude
public class AccessMethods {
    private String access_id;
    private String type; //TODO make enum
    private String region; //TODO check format
    private AccessURL access_url;

    public AccessMethods() {
    }

    public AccessMethods(String access_id, String type, String region, AccessURL access_url) {
        this.access_id = access_id;
        this.type = type;
        this.region = region;
        this.access_url = access_url;
    }

    public String getAccess_id() {
        return access_id;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public AccessURL getAccess_url() {
        return access_url;
    }

    private class AccessURL{
        private String url;
        private List<String> headers;
    }

}
