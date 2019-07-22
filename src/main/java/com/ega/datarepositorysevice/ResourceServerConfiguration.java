package com.ega.datarepositorysevice;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@Profile({"dev", "prod"})
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

}
