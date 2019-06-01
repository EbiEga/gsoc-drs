package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.BundleObjectType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xml.internal.utils.URI;

import javax.persistence.*;

@Entity
@Table(name = "bundle_object")
@JsonInclude
public class BundleObject {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BundleObjectType type;

    @Column(nullable = false)
    private URI drsUri;

    public BundleObject() {
    }

    public BundleObject(String id, String name, String type, URI drs_uri) {
        this.id = id;
        this.name = name;
        this.type = BundleObjectType.valueOf(type);
        this.drsUri = drs_uri;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("type")
    public String getType() {
        return type.toString();
    }

    @JsonProperty("drsUri")
    public URI getDrsUri() {
        return drsUri;
    }
}
