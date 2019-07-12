package com.ega.datarepositorysevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableWebFlux
@EnableResourceServer
public class DataRepositoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataRepositoryServiceApplication.class, args);
	}

}
