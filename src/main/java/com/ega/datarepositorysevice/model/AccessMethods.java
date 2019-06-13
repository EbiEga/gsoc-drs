package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "access_methods")
@JsonInclude
public class AccessMethods {
    @Id
    @GeneratedValue()
    private Long accessId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AccessMethodType type;

    @Column(nullable = false)
    private String region;


    @OneToOne
    @JoinColumn(name = "access_url")
    private AccessURL accessURL;



    @ManyToOne
    @JoinColumn(name = "object_id")
    private Object object;

    public AccessMethods() {
    }

    public AccessMethods(Long access_id, String type, String region, AccessURL accessURL, Object object) {
        this.accessId = access_id;
        this.type = AccessMethodType.createFromString(type);
        this.region = region;
        this.accessURL = accessURL;
        this.object = object;
    }

    @JsonProperty("access_id")
    public Long getAccessId() {
        return accessId;
    }

    @JsonProperty("type")
    public String getType() {
        return type.toString();
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("access_url")
    public AccessURL getAccessURL() {
        return accessURL;
    }

    @JsonIgnore
    public Object getObject() {
        return object;
    }





}
