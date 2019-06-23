package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.BundleObject;
import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.enums.BundleObjectType;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.service.BundleService;
import com.sun.org.apache.xml.internal.utils.URI;
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

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class BundleHandlerIntegrationTest {

    @Autowired
    private BundleHandler bundleHandler;
    @Autowired
    private
    BundleRepository bundleRepository;

    private Bundle bundleTestObject;


    @Before
    public void prepareDatabase() throws URI.MalformedURIException {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        BundleObject bundleObject = new BundleObject(null,"string", BundleObjectType.OBJECT,Arrays.asList(new URI("https://asdasd.com")), null);
        bundleTestObject = new Bundle(null, "string", 23, date,
                date, "string", Arrays.asList(new Checksum("string", ChecksumType.MD5_Code)
        ), "string", Arrays.asList("string"), Arrays.asList(bundleObject));

        bundleTestObject = bundleRepository.save(bundleTestObject);



    }

    @Test
    public void okTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn(bundleTestObject.getId().toString());
        Assert.assertEquals(bundleHandler.getBundle(serverRequest).block().statusCode(),HttpStatus.OK);
    }

    @Test
    public void notFoundTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("2");
        Assert.assertEquals(bundleHandler.getBundle(serverRequest).block().statusCode(),HttpStatus.NOT_FOUND);
    }
}