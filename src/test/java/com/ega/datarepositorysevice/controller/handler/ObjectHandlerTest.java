package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.service.ObjectService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;


public class ObjectHandlerTest {


    private ObjectHandler objectHandler;


    @Before
    public void PrepareEnviroment(){
        ObjectService objectService = mock(ObjectService.class);

        Object object = mock(Object.class);
        when(objectService.getObjectById(1L)).thenReturn(Mono.just(object));
        when(objectService.getObjectById(2L)).thenReturn(Mono.empty());

        objectHandler = new ObjectHandler(objectService);
    }

    @Test
    public void okTest(){
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn("1");

        Assert.assertEquals(objectHandler.getObject(request).block().statusCode(),HttpStatus.OK);
    }

    @Test
    public void notFoundTest(){
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("object_id")).thenReturn("2");

        Assert.assertEquals(objectHandler.getObject(request).block().statusCode(),HttpStatus.NOT_FOUND);
    }
}
