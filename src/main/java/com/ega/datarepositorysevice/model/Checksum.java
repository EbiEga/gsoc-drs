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
    @GeneratedValue
    private Long id;

    private String checksum;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ChecksumType type;

    @ManyToOne
    @JoinColumn(name = "bundle_object_id")
    private BundleObject bundleObject;

    @ManyToOne
    @JoinColumn(name = "object")
    private Object object;


    public Checksum() {
    }

    public Checksum(String checksum, ChecksumType type){
        this.checksum = checksum;
        this.type = type;
    }

    public Checksum(Long id, String checksum, ChecksumType type){
        this.id = id;
        this.checksum = checksum;
        this.type = type;
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
    @ManyToOne
    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Checksum)) return false;
        Checksum checksum1 = (Checksum) o;
        return Objects.equals(getChecksum(), checksum1.getChecksum()) &&
                getType().equals(checksum1.getType());
    }


}
