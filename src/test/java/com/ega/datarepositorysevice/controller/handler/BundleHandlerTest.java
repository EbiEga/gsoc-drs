package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.BundleObject;
import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.enums.BundleObjectType;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.ega.datarepositorysevice.service.BundleService;
import com.sun.org.apache.xml.internal.utils.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BundleHandlerTest {
    private BundleHandler bundleHandler;
    private Bundle bundleTestObject;

    @Before
    public void PrepareEnviroment() throws URI.MalformedURIException {
        BundleService bundleService = mock(BundleService.class);
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        BundleObject bundleObject = new BundleObject(1L, "string", BundleObjectType.OBJECT, Arrays.asList(new URI("https://asdasd.com")), null);
        bundleTestObject = new Bundle(1L, "string", 23, date,
                date, "string", Arrays.asList(new Checksum("string", ChecksumType.MD5_Code)
        ), "string", Arrays.asList("string"), Arrays.asList(bundleObject));

        when(bundleService.getBundleById(1L)).thenReturn(Mono.just(bundleTestObject));
        when(bundleService.getBundleById(2L)).thenReturn(Mono.empty());
        bundleHandler = new BundleHandler(bundleService);


    }

    @Test
    public void okTest() {
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("1");
        Assert.assertEquals(bundleHandler.getBundle(serverRequest).block().statusCode(), HttpStatus.OK);
    }

    @Test
    public void notFoundTest() {
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("2");
        Assert.assertEquals(bundleHandler.getBundle(serverRequest).block().statusCode(), HttpStatus.NOT_FOUND);
    }
}
