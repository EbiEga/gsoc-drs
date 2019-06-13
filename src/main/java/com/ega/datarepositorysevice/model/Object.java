package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;




@Entity
@Table(name = "object")
@JsonInclude
public class Object {

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

    private String mime_type;

    @Column(nullable = false)
    @OneToMany(mappedBy = "object")
    @NotEmpty
    private List<Checksum> checksums;

    @Column(nullable = false)
    @NotEmpty
    @OneToMany(mappedBy = "object")
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
        this.accessMethods = accessMethods;
        this.description = description;
        this.aliases = aliases;
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
}
