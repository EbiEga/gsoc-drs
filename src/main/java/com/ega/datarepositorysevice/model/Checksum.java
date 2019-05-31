package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "checksum")
@JsonInclude
public class Checksum {
    private String checksum;
    private String type; //TODO enum for types


    public Checksum() {
    }

    public Checksum(String checksum, String type) {
        this.checksum = checksum;
        this.type = type;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getType() {
        return type;
    }
}
