package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "bundle")
@JsonInclude
public class Bundle {
    @Id
    @GeneratedValue()
    private Long id;

    private String name;

    @Column(nullable = false)
    @NotNull
    private int size;

    @Column(nullable = false)
    @NotNull
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime created;


    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime updated;

    private String version;


    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL)
    @NotEmpty
    @ElementCollection
    private List<Checksum> checksums;

    private String description;

    @ElementCollection
    private List<String> aliases;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bundle")
    @NotEmpty
    @ElementCollection
    private List<BundleObject> contents;


    public Bundle() {
    }

    public Bundle(Long id, String name, int size, OffsetDateTime created, OffsetDateTime updated, String version,
                  List<Checksum> checksum, String description, List<String> aliases, List<BundleObject> contents) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.created = created;
        this.updated = updated;
        this.version = version;
        this.checksums = checksum;
        this.description = description;
        this.aliases = aliases;
        this.contents = contents;
        if (contents!=null){
            contents.forEach( content -> content.setBundle(this));
        }
    }

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

    @JsonProperty("checksums")
    public List<Checksum> getChecksums() {
        return checksums;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("aliases")
    public List<String> getAliases() {
        return aliases;
    }

    @JsonProperty("contents")
    public List<BundleObject> getContents() {
        return contents;
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

    public void setChecksums(List<Checksum> checksums) {
        this.checksums = checksums;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void setContents(List<BundleObject> contents) {
        this.contents = contents;
        if (contents!=null){
            contents.forEach( content -> content.setBundle(this));
        }
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Bundle)) return false;
        Bundle bundle = (Bundle) o;
        return getSize() == bundle.getSize() &&
                Objects.equals(getName(), bundle.getName()) &&
                getCreated().isEqual(bundle.getCreated()) &&
                getUpdated().isEqual(bundle.getUpdated()) &&
                Objects.equals(getVersion(), bundle.getVersion()) &&
                getChecksums().containsAll(bundle.getChecksums()) &&
                Objects.equals(getDescription(), bundle.getDescription()) &&
                getAliases().containsAll(bundle.getAliases()) &&
                getContents().containsAll(bundle.getContents());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getSize(), getCreated(), getUpdated(), getVersion(), getChecksums(), getDescription(), getAliases(), getContents());
    }
}
