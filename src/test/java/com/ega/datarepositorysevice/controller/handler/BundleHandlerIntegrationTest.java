package com.ega.datarepositorysevice.controller.handler;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.BundleObject;
import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.enums.BundleObjectType;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.utils.TestObjectCreator;
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
import reactor.core.publisher.Mono;

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
        BundleObject bundleObject = new BundleObject(null, "string", BundleObjectType.OBJECT, Arrays.asList(new URI("https://asdasd.com")), null);
        bundleTestObject = new Bundle(null, "string", 23, date,
                date, "string", Arrays.asList(new Checksum("string", ChecksumType.MD5_Code)
        ), "string", Arrays.asList("string"), Arrays.asList(bundleObject));

        bundleTestObject = bundleRepository.save(bundleTestObject);


    }

    @Test
    public void getOkTest() throws URI.MalformedURIException {
        Bundle bundle = saveBundle();
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn(bundle.getId().toString());
        Assert.assertEquals(bundleHandler.getBundle(serverRequest).block().statusCode(), HttpStatus.OK);
    }

    @Test
    public void getNotFoundTest() {
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("2");
        Assert.assertEquals(bundleHandler.getBundle(serverRequest).block().statusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getBadRequestTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("id");
        Assert.assertEquals(bundleHandler.getBundle(serverRequest).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void deleteBadRequestTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("id");

        Assert.assertEquals(bundleHandler.deleteBundle(serverRequest).block().statusCode(), HttpStatus.BAD_REQUEST);


    }

    @Test
    public void deleteNotFoundTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("2");

        Assert.assertEquals(bundleHandler.deleteBundle(serverRequest).block().statusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void deleteOkTest() throws URI.MalformedURIException {
        Bundle bundle = saveBundle();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn(bundle.getId().toString());

        Assert.assertEquals(bundleHandler.deleteBundle(serverRequest).block().statusCode(), HttpStatus.OK);

    }

    @Test
    public void updateBadRequestBodyTest() throws URI.MalformedURIException {
        Bundle bundle = saveBundle();
        bundle.setCreated(null);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn(bundle.getId().toString());
        when(serverRequest.bodyToMono(Bundle.class)).thenReturn(Mono.just(bundle));

        Assert.assertEquals(bundleHandler.updateBundle(serverRequest).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void updateBadRequestParameterTest() throws URI.MalformedURIException {

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("id");
        when(serverRequest.bodyToMono(Bundle.class)).thenReturn(Mono.empty());

        Assert.assertEquals(bundleHandler.updateBundle(serverRequest).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void updateEmptyBodyTest() throws URI.MalformedURIException {
        Bundle bundle = saveBundle();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn(bundle.getId().toString());
        when(serverRequest.bodyToMono(Bundle.class)).thenReturn(Mono.empty());


        Assert.assertEquals(bundleHandler.updateBundle(serverRequest).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void updateNotFoundTest() throws URI.MalformedURIException {
        Bundle bundle = TestObjectCreator.getBundle();
        bundle.setId(0L);

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn("0");
        when(serverRequest.bodyToMono(Bundle.class)).thenReturn(Mono.just(bundle));


        Assert.assertEquals(bundleHandler.updateBundle(serverRequest).block().statusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void updateOkTest() throws URI.MalformedURIException {
        Bundle bundle = saveBundle();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.pathVariable("bundle_id")).thenReturn(bundle.getId().toString());
        when(serverRequest.bodyToMono(Bundle.class)).thenReturn(Mono.just(bundle));

        Assert.assertEquals(bundleHandler.updateBundle(serverRequest).block().statusCode(), HttpStatus.OK);

    }

    @Test
    public void saveOkTest() throws URI.MalformedURIException {
        Bundle bundle = TestObjectCreator.getBundle();
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.bodyToMono(Bundle.class)).thenReturn(Mono.just(bundle));


        Assert.assertEquals(bundleHandler.saveBundle(serverRequest).block().statusCode(), HttpStatus.CREATED);

    }

    @Test
    public void savedBadRequestTest() throws URI.MalformedURIException {
        Bundle bundle = TestObjectCreator.getBundle();
        bundle.setCreated(null);
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.bodyToMono(Bundle.class)).thenReturn(Mono.just(bundle));

        Assert.assertEquals(bundleHandler.saveBundle(serverRequest).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void savedEmptyBodyTest(){
        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.bodyToMono(Bundle.class)).thenReturn(Mono.empty());

        Assert.assertEquals(bundleHandler.updateBundle(serverRequest).block().statusCode(), HttpStatus.BAD_REQUEST);

    }

    private Bundle saveBundle() throws URI.MalformedURIException {
        return bundleRepository.save(TestObjectCreator.getBundle());
    }
}