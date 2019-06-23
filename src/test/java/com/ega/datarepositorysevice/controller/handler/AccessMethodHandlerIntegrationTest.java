package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.controller.Router;
import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.AccessURL;
import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import com.ega.datarepositorysevice.service.BundleService;
import com.ega.datarepositorysevice.service.ObjectService;
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
import org.springframework.test.web.reactive.server.WebTestClient;
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
    private AccessMethodHandler accessMethodHandler;

    private AccessMethods accessMethodsTestObject;


    @Before
    public void PrepareEnviroment(){
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL(null,"https://www.youtube.com/watch?v=nsoIcQYlPxg", map);
        accessMethodsTestObject = new AccessMethods(null, AccessMethodType.S3, "region", accessURL);
        accessMethodsTestObject = accessMethodsRepository.save(accessMethodsTestObject);
    }

    @Test
    public void okTest(){
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("access_id")).thenReturn("1");
        Mono<ServerResponse> actualMono = accessMethodHandler.getAccess(request);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.OK);
    }

    @Test
    public void notFoundTest(){
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("access_id")).thenReturn("2");
        Mono<ServerResponse> actualMono = accessMethodHandler.getAccess(request);
        Assert.assertEquals(Objects.requireNonNull(actualMono.block()).statusCode(), HttpStatus.NOT_FOUND);

    }
}

