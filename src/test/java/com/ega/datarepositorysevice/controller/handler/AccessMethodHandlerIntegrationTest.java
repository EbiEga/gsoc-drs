package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class AccessMethodHandlerIntegrationTest {

    @Autowired
    private AccessMethodsRepository accessMethodsRepository;

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private AccessMethodHandler accessMethodHandler;

    private AccessMethods accessMethodsTestObject;


    @Before
    public void PrepareEnviroment() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL(null, "https://www.youtube.com/watch?v=nsoIcQYlPxg", map);
        accessMethodsTestObject = new AccessMethods(null, AccessMethodType.S3, "region", accessURL);
        accessMethodsTestObject = accessMethodsRepository.save(accessMethodsTestObject);
    }

    @Test
    public void getOkTest() {
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = object.getAccessMethods().get(0);

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(request.pathVariable("access_id")).thenReturn(accessMethods.getAccessId().toString());

        Mono<ServerResponse> actualMono = accessMethodHandler.getAccess(request);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.OK);
    }

    @Test
    public void getAccessNotFoundTest() {
        Object object = saveObjectWIthAccessMethod();

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(request.pathVariable("access_id")).thenReturn("0");
        Mono<ServerResponse> actualMono = accessMethodHandler.getAccess(request);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void getObjectNotFoundTest() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn("0");
        when(request.pathVariable("access_id")).thenReturn("0");
        Mono<ServerResponse> actualMono = accessMethodHandler.getAccess(request);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void getBadRequestTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("access_id")).thenReturn("id");
        when(serverRequest.pathVariable("object_id")).thenReturn("id");

        Mono<ServerResponse> actualMono = accessMethodHandler.getAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.BAD_REQUEST);


    }

    @Test
    public void deleteBadRequestTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("access_id")).thenReturn("id");
        when(serverRequest.pathVariable("object_id")).thenReturn("id");

        Mono<ServerResponse> actualMono = accessMethodHandler.deleteAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteAccessNotFoundTest(){
        Object object = saveObjectWIthAccessMethod();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("access_id")).thenReturn("0");
        when(serverRequest.pathVariable("object_id")).thenReturn(object.getId().toString());

        Mono<ServerResponse> actualMono = accessMethodHandler.deleteAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteObjectNotFoundTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("access_id")).thenReturn("0");
        when(serverRequest.pathVariable("object_id")).thenReturn("0");

        Mono<ServerResponse> actualMono = accessMethodHandler.deleteAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteLastAccessMethodTest(){
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = object.getAccessMethods().get(0);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(serverRequest.pathVariable("access_id")).thenReturn(accessMethods.getAccessId().toString());

        Mono<ServerResponse> actualMono = accessMethodHandler.deleteAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteOkTest(){
        Object unsavedObject = TestObjectCreator.getObject();
        unsavedObject.addAccessMethod(TestObjectCreator.getAccessMethods());
        AccessMethods secondAccessMethods = TestObjectCreator.getAccessMethods();
        secondAccessMethods.setRegion("new region");
        unsavedObject.addAccessMethod(secondAccessMethods);
        Object object = objectRepository.save(unsavedObject);
        AccessMethods accessMethods = object.getAccessMethods().get(0);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(serverRequest.pathVariable("access_id")).thenReturn(accessMethods.getAccessId().toString());

        Mono<ServerResponse> actualMono = accessMethodHandler.deleteAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.OK);
    }

    @Test
    public void updateBadRequestTest(){
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = object.getAccessMethods().get(0);
        accessMethods.setAccessURL(null);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(serverRequest.pathVariable("access_id")).thenReturn(accessMethods.getAccessId().toString());
        when(serverRequest.bodyToMono(AccessMethods.class)).thenReturn(Mono.just(accessMethods));
        Mono<ServerResponse> actualMono = accessMethodHandler.updateAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateAccessNotFoundTest(){
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = object.getAccessMethods().get(0);
        accessMethods.setAccessURL(null);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(serverRequest.pathVariable("access_id")).thenReturn("0");
        when(serverRequest.bodyToMono(AccessMethods.class)).thenReturn(Mono.just(accessMethods));
        Mono<ServerResponse> actualMono = accessMethodHandler.updateAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateObjectNotFoundTest(){
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = object.getAccessMethods().get(0);
        accessMethods.setAccessURL(null);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn("0");
        when(serverRequest.pathVariable("access_id")).thenReturn(accessMethods.getAccessId().toString());
        when(serverRequest.bodyToMono(AccessMethods.class)).thenReturn(Mono.just(accessMethods));
        Mono<ServerResponse> actualMono = accessMethodHandler.updateAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateOkTest(){
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = object.getAccessMethods().get(0);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(serverRequest.pathVariable("access_id")).thenReturn(accessMethods.getAccessId().toString());
        when(serverRequest.bodyToMono(AccessMethods.class)).thenReturn(Mono.just(accessMethods));
        Mono<ServerResponse> actualMono = accessMethodHandler.updateAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.OK);
    }

    @Test
    public void saveOkTest(){
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = TestObjectCreator.getAccessMethods();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(serverRequest.bodyToMono(AccessMethods.class)).thenReturn(Mono.just(accessMethods));
        Mono<ServerResponse> actualMono = accessMethodHandler.saveAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.OK);
    }

    @Test
    public void savedBadRequestTest(){
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = TestObjectCreator.getAccessMethods();
        accessMethods.setAccessURL(null);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn(object.getId().toString());
        when(serverRequest.bodyToMono(AccessMethods.class)).thenReturn(Mono.just(accessMethods));
        Mono<ServerResponse> actualMono = accessMethodHandler.saveAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void savedObjectNotFoundTest(){
        Object object = saveObjectWIthAccessMethod();
        AccessMethods accessMethods = TestObjectCreator.getAccessMethods();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("object_id")).thenReturn("0");
        when(serverRequest.bodyToMono(AccessMethods.class)).thenReturn(Mono.just(accessMethods));
        Mono<ServerResponse> actualMono = accessMethodHandler.saveAccess(serverRequest);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);
    }

    public Object saveObjectWIthAccessMethod(){
        Object object = TestObjectCreator.getObject();
        return objectRepository.save(object);
    }
}

