package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.utils.TestObjectCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class ObjectHandlerIntegrationTest {

    @Autowired
    private ObjectHandler objectHandler;

    @Autowired
    private ObjectRepository objectRepository;

    @Test
    public void getOkTest() {
        Object object = saveObject();
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn(object.getId().toString());
        Assert.assertEquals(objectHandler.getObject(request).block().statusCode(), HttpStatus.OK);
    }

    @Test
    public void getNotFoundTest() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn("0");
        ServerResponse response = objectHandler.getObject(request).block();
        Assert.assertEquals(response.statusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getBadRequestTest() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn("id");
        Assert.assertEquals(objectHandler.getObject(request).block().statusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getEmptyRequestTest() {
        ServerRequest request = mock(ServerRequest.class);
        Assert.assertEquals(objectHandler.getObject(request).block().statusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteBadRequestTest() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn("id");

        Assert.assertEquals(objectHandler.deleteObject(request).block().statusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteNotFoundTest() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn("0");

        Assert.assertEquals(objectHandler.deleteObject(request).block().statusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void deleteOkTest() {
        Object object = saveObject();

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn(object.getId().toString());
        Mono<ServerResponse> responseMono = objectHandler.deleteObject(request);
        Assert.assertEquals(responseMono.block().statusCode(), HttpStatus.OK);
        Assert.assertTrue(objectRepository.existsById(1L));

    }

    @Test
    public void updateBadRequestTest() {
        Object object = saveObject();
        object.setAccessMethods(new ArrayList<>());
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(request.bodyToMono(Object.class)).thenReturn(Mono.just(object));
        Assert.assertEquals(objectHandler.updateObject(request).block().statusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateEmptyBodyTest() {
        Object object = saveObject();

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(request.bodyToMono(Object.class)).thenReturn(Mono.empty());

        Assert.assertEquals(objectHandler.updateObject(request).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void updateNotFoundTest() {
        Object object = saveObject();
        object.setId(0L);
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn("0");
        when(request.bodyToMono(Object.class)).thenReturn(Mono.just(object));

        Assert.assertEquals(objectHandler.updateObject(request).block().statusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void updateOkTest() {
        Object object = saveObject();

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(request.bodyToMono(Object.class)).thenReturn(Mono.just(object));

        Assert.assertEquals(objectHandler.deleteObject(request).block().statusCode(), HttpStatus.OK);

    }

    @Test
    public void saveOkTest() {
        Object object = TestObjectCreator.getObject();

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(Object.class)).thenReturn(Mono.just(object));

        Assert.assertEquals(objectHandler.saveObject(request).block().statusCode(), HttpStatus.OK);

    }

    @Test
    public void saveBadRequestTest() {
        Object object = TestObjectCreator.getObject();
        object.setAccessMethods(new ArrayList<>());

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(Object.class)).thenReturn(Mono.just(object));

        Assert.assertEquals(objectHandler.saveObject(request).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void saveEmptyBodyTest() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(Object.class)).thenReturn(Mono.empty());

        Assert.assertEquals(objectHandler.saveObject(request).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    private Object saveObject() {
        return objectRepository.save(TestObjectCreator.getObject());
    }
}
