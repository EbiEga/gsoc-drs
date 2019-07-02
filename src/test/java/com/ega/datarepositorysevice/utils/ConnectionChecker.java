package com.ega.datarepositorysevice.utils;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionChecker {
    @Value("${spring.datasource.url}")
    private String uri;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.username}")
    private String username;



    public boolean connect() {
        try (Connection conn = DriverManager.getConnection(
                uri, username, password)) {

            return conn != null;

        } catch (Exception e) {
            return false;
        }
    }
}
