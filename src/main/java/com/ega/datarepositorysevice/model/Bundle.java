package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

import static com.ega.datarepositorysevice.utils.Constants.DATE_TIME_FORMAT;

@Entity
@Table(name = "bundle")
@JsonInclude
public class Bundle {
    @Id
    @NotEmpty
    private String id;

    private String name;

    @Column(nullable = false)
    @NonNull
    private int size;

    @Column(nullable = false)
    @NonNull
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private Date created;

    @Column(nullable = false)
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private Date updated;

    private String version;

    @Column(nullable = false)
    @OneToMany(mappedBy = "bundle_object_id")
    @NotEmpty
    private List<Checksum> checksum;

    private String description;

    private List<String> aliases;

    @Column(nullable = false)
    @OneToMany(mappedBy = "bundle_id")
    @NotEmpty
    private List<BundleObject> contents;


    public Bundle() {
    }

    public Bundle(String id, String name, int size, Date created, Date updated, String version,
                  List<Checksum> checksum, String description, List<String> aliases, List<BundleObject> contents) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.created = created;
        this.updated = updated;
        this.version = version;
        this.checksum = checksum;
        this.description = description;
        this.aliases = aliases;
        this.contents = contents;
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

    @JsonProperty("checksum")
    public List<Checksum> getChecksum() {
        return checksum;
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
