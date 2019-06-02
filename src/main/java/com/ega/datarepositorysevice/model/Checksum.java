package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "checksum")
@JsonInclude
public class Checksum {

    @Id
    private String checksum;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChecksumType type;

    @ManyToOne
    @JoinColumn(name = "bundle_object_id")
    private BundleObject bundleObject;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private Object object;


    public Checksum() {
    }

    public Checksum(String checksum, String type, BundleObject bundleObject, Object object) {
        this.checksum = checksum;
        this.type = ChecksumType.valueOf(type);
        this.bundleObject = bundleObject;
        this.object = object;
    }

    @JsonProperty("checksum")
    public String getChecksum() {
        return checksum;
    }

    @JsonProperty("type")
    public String getType() {
        return type.toString();
    }

    @JsonIgnore
    public BundleObject getBundleObject() {
        return bundleObject;
    }

    @JsonIgnore
    public Object getObject() {
        return object;
    }
}
