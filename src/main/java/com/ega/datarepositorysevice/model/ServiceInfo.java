package com.ega.datarepositorysevice.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "service_info")
public class ServiceInfo {
    @Id
    @NonNull
    private  String version;
    @NonNull private  String description;
    @NonNull private  String title;
    @NonNull private  String contact;
    @NonNull private  String licence;

    protected ServiceInfo() {}

    public ServiceInfo(String version, String description, String title, String contact, String licence){
        this.version = version;
        this.description = description;
        this.title = title;
        this.contact = contact;
        this.licence = licence;
    }
}
