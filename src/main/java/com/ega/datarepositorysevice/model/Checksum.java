package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "checksum")
@JsonInclude
public class Checksum {

    @Id
    private String checksum;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChecksumType type;


    public Checksum() {
    }

    public Checksum(String checksum, String type) {
        this.checksum = checksum;
        this.type = ChecksumType.valueOf(type);
    }

    @JsonProperty("checksum")
    public String getChecksum() {
        return checksum;
    }

    @JsonProperty("type")
    public String getType() {
        return type.toString();
    }
}
