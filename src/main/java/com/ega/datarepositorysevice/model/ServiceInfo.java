package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "service_info")
@JsonInclude
public class ServiceInfo {
    @Id
    private  String version;

    @Column(nullable = false)
    private  String description;

    @Column(nullable = false)
    @NonNull private  String title;

    @Column(nullable = false)
    @NonNull private  String contact;

    @Column(nullable = false)
    @NonNull private  String licence;

    protected ServiceInfo() {}

    public ServiceInfo(String version, String description, String title, String contact, String licence){
        this.version = version;
        this.description = description;
        this.title = title;
        this.contact = contact;
        this.licence = licence;
    }
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("contact")
    public String getContact() {
        return contact;
    }

    @JsonProperty("licence")
    public String getLicence() {
        return licence;
    }
}
