package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "checksum")
@JsonInclude
public class Checksum {

    @Id
    private String checksum;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ChecksumType type;

    @ManyToOne
    @JoinColumn(name = "bundle_object_id")
    private BundleObject bundleObject;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private Object object;


    public Checksum() {
    }

    public Checksum(String checksum, ChecksumType type){
        this.checksum = checksum;
        this.type = type;
    }

    public Checksum(String checksum, ChecksumType type, BundleObject bundleObject, Object object) {
        this.checksum = checksum;
        this.type = type;
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Checksum)) return false;
        Checksum checksum1 = (Checksum) o;
        return Objects.equals(getChecksum(), checksum1.getChecksum()) &&
                getType() == checksum1.getType() &&
                Objects.equals(getBundleObject(), checksum1.getBundleObject()) &&
                Objects.equals(getObject(), checksum1.getObject());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getChecksum(), getType(), getBundleObject(), getObject());
    }
}
