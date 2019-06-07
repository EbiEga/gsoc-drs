package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "access_methods")
@JsonInclude
public class AccessMethods {
    @Id
    @GeneratedValue
    private String accessId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private AccessMethodType type;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "access_url")
    private AccessURL accessURL;

    public AccessMethods() {
    }

    public AccessMethods(String access_id, String type, String region, AccessURL accessURL) {
        this.accessId = access_id;
        this.type = AccessMethodType.createFromString(type);
        this.region = region;
        this.accessURL = accessURL;
    }

    @JsonProperty("access_id")
    public String getAccess_id() {
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



}
