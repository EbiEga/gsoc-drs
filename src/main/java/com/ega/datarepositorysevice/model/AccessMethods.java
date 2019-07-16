package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "access_methods")
@JsonInclude
public class AccessMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AccessMethodType type;

    @Column(nullable = false)
    private String region;


    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private AccessURL accessURL;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "object")
    private Object object;


    public AccessMethods() {
    }


    public AccessMethods( AccessMethodType type, String region, AccessURL accessURL) {
        //this.accessId = access_id;
        this.type = type;
        this.region = region;
        this.accessURL = accessURL;
        accessURL.setMethods(this);
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

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public void setType(AccessMethodType type) {
        this.type = type;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAccessURL(AccessURL accessURL) {
        this.accessURL = accessURL;
    }

    public void setObject(Object object) {
        this.object = object;
        object.addAccessMethod(this);

    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessMethods)) return false;
        AccessMethods that = (AccessMethods) o;
        return getType().equals(that.getType()) &&
                Objects.equals(getRegion(), that.getRegion()) &&
                Objects.equals(getAccessURL(), that.getAccessURL());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAccessId(), getType(), getRegion(), getAccessURL());
    }
}
