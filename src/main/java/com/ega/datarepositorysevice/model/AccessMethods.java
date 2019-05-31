package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "access_methods")
@JsonInclude
public class AccessMethods {
    private String access_id;
    private String type; //TODO make enum
    private String region; //TODO check format
    private AccessURL access_url;

    public AccessMethods() {
    }

    public AccessMethods(String access_id, String type, String region, AccessURL access_url) {
        this.access_id = access_id;
        this.type = type;
        this.region = region;
        this.access_url = access_url;
    }

    @JsonProperty("access_id")
    public String getAccess_id() {
        return access_id;
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
    public AccessURL getAccess_url() {
        return access_url;
    }

    private class AccessURL{
        private String url;
        private List<String> headers;

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
    }

}
