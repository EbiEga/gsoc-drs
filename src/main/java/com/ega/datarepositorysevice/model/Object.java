package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "object")
@JsonInclude
public class Object {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    @NotNull(message = "size must not be null")
    private int size;

    @Column(nullable = false)
    @NotNull(message = "created must not be null")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime created;

    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime updated;

    private String version;

    private String mime_type;

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL)
    @NotEmpty(message = "checksums must contains at least one element")
    private List<Checksum> checksums;

    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true)
    @NotEmpty(message = "accessMethods must contains at least one element")
    @ElementCollection
    private List<AccessMethods> accessMethods;

    private String description;

    @ElementCollection

    private List<String> aliases;

    public Object() {
    }

    public Object(Long id, String name, int size, OffsetDateTime created, OffsetDateTime updated, String version, String mime_type,
                  List<Checksum> checksums, List<AccessMethods> accessMethods, String description, List<String> aliases) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.created = created;
        this.updated = updated;
        this.version = version;
        this.mime_type = mime_type;
        this.checksums = checksums;
        checksums.forEach(checksum -> checksum.setObject(this));
        this.accessMethods = accessMethods;
        if (accessMethods != null) {
            accessMethods.forEach(accessMethod -> accessMethod.setObject(this));
        }
        this.description = description;
        this.aliases = aliases == null ? null : new ArrayList<>();
    }

//    @PrePersist
//    @PreUpdate
//    public void updateAddressAssociation(){
//        for(AccessMethods address : this.accessMethods){
//            if(address.getObject()==null) {
//                address.setObject(this);
//            }
//        }
//    }
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("size")
    public int getSize() {
        return size;
    }

    @JsonProperty("created")
    public OffsetDateTime getCreated() {
        return created;
    }

    @JsonProperty("updated")
    public OffsetDateTime getUpdated() {
        return updated;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("mime_type")
    public String getMime_type() {
        return mime_type;
    }

    @JsonProperty("checksums")
    public List<Checksum> getChecksums() {
        return checksums;
    }

    @JsonProperty("access_methods")
    public List<AccessMethods> getAccessMethods() {
        return accessMethods;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("aliases")
    public List<String> getAliases() {
        return aliases;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public void setUpdated(OffsetDateTime updated) {
        this.updated = updated;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public void setChecksums(List<Checksum> checksums) {
        this.checksums = checksums;
    }

    public void setAccessMethods(List<AccessMethods> accessMethods) {
        this.accessMethods = accessMethods;
        if(accessMethods!= null){
            accessMethods.forEach( method -> {method.setObject(this); });
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void addAccessMethod(AccessMethods methods){
        if (accessMethods != null && !accessMethods.contains(methods)){
            accessMethods.add(methods);
        }
    }

    public boolean deleteAccessMethod(Long id){
        if (accessMethods != null){
            return accessMethods.removeIf(accessMethod -> accessMethod.getAccessId().equals(id));
        }
        return false;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Object)) return false;
        Object object = (Object) o;
        return getSize() == object.getSize() &&
                Objects.equals(getName(), object.getName()) &&
                getCreated().isEqual(object.getCreated()) &&
                getUpdated().isEqual(object.getUpdated()) &&
                Objects.equals(getVersion(), object.getVersion()) &&
                Objects.equals(getMime_type(), object.getMime_type()) &&
                getChecksums().containsAll(object.getChecksums()) &&
                getAccessMethods().containsAll(object.getAccessMethods()) &&
                Objects.equals(getDescription(), object.getDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getSize(), getCreated(), getUpdated(), getVersion(), getMime_type(), getChecksums(), getAccessMethods(), getDescription(), getAliases());
    }
}
