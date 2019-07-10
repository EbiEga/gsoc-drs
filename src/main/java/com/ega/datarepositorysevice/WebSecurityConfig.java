package com.ega.datarepositorysevice;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


@EnableWebFluxSecurity
@EnableResourceServer
public class WebSecurityConfig{

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder decoder(){
        return new CustomDecoder();
    }
    public class CustomDecoder implements ReactiveJwtDecoder{

        @Override
        public Mono<Jwt> decode(String s) throws JwtException {
            System.out.println(s);
            return Mono.just(new Jwt(s,null,null,null,null));
        }
    }
}

