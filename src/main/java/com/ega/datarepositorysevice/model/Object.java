package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "object")
@JsonInclude
public class Object {
    private String id;
    private String name;
    private int size;
    private Date created;
    private Date updated;
    private String version;
    private String mime_type; //TODO enums for mediatypes
    private List<Checksum> checksums;
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getVersion() {
        return version;
    }

    public String getMime_type() {
        return mime_type;
    }

    public List<Checksum> getChecksums() {
        return checksums;
    }

    public List<AccessMethods> getAccessMethods() {
        return accessMethods;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliaces() {
        return aliases;
    }
}
