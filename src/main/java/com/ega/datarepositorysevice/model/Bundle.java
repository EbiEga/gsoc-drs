package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.OffsetDateTime;
import java.util.List;


@Entity
@Table(name = "bundle")
@JsonInclude
public class Bundle {
    @Id
    @GeneratedValue()
    private Long id;

    private String name;

    @Column(nullable = false)
    @NonNull
    private int size;

    @Column(nullable = false)
    @NonNull
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime created;

    @Column(nullable = false)
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime updated;

    private String version;

    @Column(nullable = false)
    @OneToMany(mappedBy = "bundleObject")
    @NotEmpty
    private List<Checksum> checksums;

    private String description;

    @ElementCollection
    private List<String> aliases;

    @Column(nullable = false)
    @OneToMany(mappedBy = "bundle")
    @NotEmpty
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
    public List<Checksum> getChecksum() {
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
}
