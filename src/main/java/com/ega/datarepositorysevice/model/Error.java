package com.ega.datarepositorysevice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@JsonInclude
public class Error {
    private String msg;
    private HttpStatus statusCode;


    public Error(){}

    public Error(String msg, HttpStatus statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    @JsonCreator
    public Error(@JsonProperty("msg") String msg,
                 @JsonProperty("status_code") int statusCode) {
        this.msg = msg;
        this.statusCode = HttpStatus.valueOf(statusCode);
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    @JsonProperty("status_code")
    public int getStatusCode() {
        return statusCode.value();
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (!(o instanceof Error)) return false;
        Error error = (Error) o;
        return getStatusCode() == error.getStatusCode() &&
                Objects.equals(getMsg(), error.getMsg());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getMsg(), getStatusCode());
    }
}
