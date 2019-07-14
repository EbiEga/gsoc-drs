package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.BundleObjectType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xml.internal.utils.URI;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bundle_object")
@JsonInclude
public class BundleObject {
    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    @Pattern(regexp = "[^/]*")
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private BundleObjectType type;

    @ElementCollection
    private List<URI> drsUri;

    @ManyToOne
    @JoinColumn
    private Bundle bundle;

    public BundleObject() {
    }

    public BundleObject(Long id, String name, BundleObjectType type, List<URI> drs_uri, Bundle bundle) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.drsUri = drs_uri;
        this.bundle = bundle;
    }

    @JsonProperty("id")
    public Long getId() {
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

    @JsonProperty("drs_uri")
    public List<URI> getDrsUri() {
        return drsUri;
    }

    @JsonIgnore
    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(BundleObjectType type) {
        this.type = type;
    }

    public void setDrsUri(List<URI> drsUri) {
        this.drsUri = drsUri;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof BundleObject)) return false;
        BundleObject that = (BundleObject) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                getType().equals(that.getType()) &&
                getDrsUri().containsAll(that.getDrsUri());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getType(), getDrsUri(), getBundle());
    }
}
