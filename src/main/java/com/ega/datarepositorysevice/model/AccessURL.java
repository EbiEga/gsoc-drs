package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "access_url")
@JsonInclude
public class AccessURL{

    @Id
    @NotEmpty
    @URL
    private String url;
    @ElementCollection
    private Map<String, String> headers;

    @OneToOne(mappedBy = "accessURL")
    private AccessMethods methods;

    public AccessURL() {
    }

    public AccessURL(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("headers")
    public Map<String, String> getHeaders() {
        return headers;
    }

    @JsonIgnore
    @OneToOne(mappedBy = "accessURL")
    public AccessMethods getMethods() {
        return methods;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessURL)) return false;
        AccessURL accessURL = (AccessURL) o;
        return Objects.equals(getUrl(), accessURL.getUrl()) &&
                Objects.equals(getHeaders(), accessURL.getHeaders()) &&
                Objects.equals(getMethods(), accessURL.getMethods());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUrl(), getHeaders(), getMethods());
    }
}
