package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bundle")
@JsonInclude
public class Bundle {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date updated;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private List<Checksum> checksum;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private List<String> aliases;

    @Column(nullable = false)
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
