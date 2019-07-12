package com.ega.datarepositorysevice.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class AuthServerConnectionChecker implements RemoteConnectionChecker {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String uri;

    @Override
    public boolean connect() {
        try {
            InetAddress address = InetAddress.getByName(uri);
            try {
                return address.isReachable(5000);
            } catch (IOException e) {
                return false;
            }
        } catch (UnknownHostException e) {
            return false;
        }

    }
}
