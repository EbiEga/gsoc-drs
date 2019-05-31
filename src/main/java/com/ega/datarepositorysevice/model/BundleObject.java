package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.org.apache.xml.internal.utils.URI;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bundle_object")
@JsonInclude
public class BundleObject {
    private String id;
    private String name;
    private String type; // TODO make enum
    private URI drs_uri;

    public BundleObject() {
    }

    public BundleObject(String id, String name, String type, URI drs_uri) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.drs_uri = drs_uri;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public URI getDrs_uri() {
        return drs_uri;
    }
}
