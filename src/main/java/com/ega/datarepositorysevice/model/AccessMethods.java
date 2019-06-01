package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "access_methods")
@JsonInclude
public class AccessMethods {
    @Id
    @GeneratedValue
    private String accessId;

    @Column(nullable = false)
    private String type; //TODO make enum

    @Column(nullable = false)
    private String region; //TODO check format

    @Column(nullable = false)
    private AccessURL accessURL;

    public AccessMethods() {
    }

    public AccessMethods(String access_id, String type, String region, AccessURL accessURL) {
        this.accessId = access_id;
        this.type = type;
        this.region = region;
        this.accessURL = accessURL;
    }

    @JsonProperty("access_id")
    public String getAccess_id() {
        return accessId;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("access_url")
    @OneToOne
    @JoinColumn(name = "access_url")
    public AccessURL getAccessURL() {
        return accessURL;
    }

    @Entity
    @Table(name = "access_url")
    @JsonInclude
    private class AccessURL{

        @Id
        private String url;
        private List<String> headers;

        @Column(nullable = false)
        private AccessMethods methods;

        public AccessURL() {
        }

        public AccessURL(String url, List<String> headers) {
            this.url = url;
            this.headers = headers;
        }

        @JsonProperty("url")
        public String getUrl() {
            return url;
        }

        @JsonProperty("headers")
        public List<String> getHeaders() {
            return headers;
        }

        @JsonIgnore
        @OneToOne(mappedBy = "accessURL")
        public AccessMethods getMethods() {
            return methods;
        }
    }

}
