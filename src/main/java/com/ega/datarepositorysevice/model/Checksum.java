package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "checksum")
@JsonInclude
public class Checksum {

    @Id
    private String checksum;

    @Column(nullable = false)
    private String type; //TODO enum for types


    public Checksum() {
    }

    public Checksum(String checksum, String type) {
        this.checksum = checksum;
        this.type = type;
    }

    @JsonProperty("checksum")
    public String getChecksum() {
        return checksum;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }
}
