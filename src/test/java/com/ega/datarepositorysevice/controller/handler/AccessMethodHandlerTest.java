package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import com.ega.datarepositorysevice.service.ObjectService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AccessMethodHandlerTest {

    private AccessMethodHandler accessMethodHandler;
    private AccessMethods accessMethods;

    @Before
    public void PrepareEnviroment() {
        AccessMethodsService accessMethodsService = mock(AccessMethodsService.class);
        ObjectService objectService = mock(ObjectService.class);
        accessMethods = new AccessMethods(1L, AccessMethodType.S3,
                "region", new AccessURL());
        when(accessMethodsService.getAccessMethodsById(1L, 1L)).thenReturn(Mono.just(accessMethods));
        when(accessMethodsService.getAccessMethodsById(1L, 2L)).thenReturn(Mono.empty());
        accessMethodHandler = new AccessMethodHandler(accessMethodsService, objectService);

    }


    @Test
    public void okTest() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("access_id")).thenReturn("1");
        when(request.pathVariable("object_id")).thenReturn("1");

        Mono<ServerResponse> actualMono = accessMethodHandler.getAccess(request);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.OK);
    }

    @Test
    public void notFoundTest() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("access_id")).thenReturn("2");
        when(request.pathVariable("object_id")).thenReturn("1");

        Mono<ServerResponse> actualMono = accessMethodHandler.getAccess(request);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);
    }
}
