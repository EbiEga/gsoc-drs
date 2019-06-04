package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.BundleObjectType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xml.internal.utils.URI;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "bundle_object")
@JsonInclude
public class BundleObject {
    @Id
    @NotEmpty
    private String id;

    @Column(nullable = false)
    @NotEmpty
    @Pattern(regexp = "[^/]*")
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private BundleObjectType type;

    @Column(nullable = false)
    private URI drsUri;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "bundle_id")
    private Bundle bundle;

    public BundleObject() {
    }

    public BundleObject(String id, String name, String type, URI drs_uri, Bundle bundle) {
        this.id = id;
        this.name = name;
        this.type = BundleObjectType.valueOf(type);
        this.drsUri = drs_uri;
        this.bundle = bundle;
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

    @JsonIgnore
    public Bundle getBundle() {
        return bundle;
    }
}
