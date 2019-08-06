package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "access_url")
@JsonInclude
public class AccessURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public AccessURL(Long id, String url, Map<String, String> headers) {
        this.id = id;
        this.url = url;
        this.headers = headers;
    }

    @JsonIgnore
    public Long getId() {
        return id;
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
    public AccessMethods getMethods() {
        return methods;
    }

    public void setMethods(AccessMethods methods) {
        this.methods = methods;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessURL)) return false;
        AccessURL accessURL = (AccessURL) o;
        return Objects.equals(getUrl(), accessURL.getUrl()) &&
                getHeaders().equals(accessURL.getHeaders())
                ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
