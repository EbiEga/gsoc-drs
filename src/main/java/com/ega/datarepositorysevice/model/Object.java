package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.DataStoreUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;




@Entity
@Table(name = "object")
@JsonInclude
public class Object {

    @Id
    @NotEmpty
    private String id;

    private String name;

    @Column(nullable = false)
    @NonNull
    private int size;

    @Column(nullable = false)
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) //TODO create global datetime config
    private Date created;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updated;

    private String version;

    private String mime_type;

    @Column(nullable = false)
    @OneToMany(mappedBy = "object_id")
    @NotEmpty
    private List<Checksum> checksums;

    @Column(nullable = false)
    @NotEmpty
    private List<AccessMethods> accessMethods;

    private String description;

    private List<String> aliases;

    public Object() {
    }

    public Object(String id, String name, int size, Date created, Date updated, String version, String mime_type,
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
    public String getId() {
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
    public Date getCreated() {
        return created;
    }

    @JsonProperty("updated")
    public Date getUpdated() {
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
    public List<String> getAliaces() {
        return aliases;
    }
}
