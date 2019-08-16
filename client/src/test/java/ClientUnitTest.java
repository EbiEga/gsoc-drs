import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.net.URI;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class ClientUnitTest {
    private String host = "";
    private final MockWebServer mockWebServer = new MockWebServer();
    private JacksonTester<Object> json;


    private ClientDRS clientDRS;
    private String defaultToken = "token";
    private String defaultHost = "host";
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    WebClient webClient;

    ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void createClient(){
        clientDRS = new ClientDRS(mockWebServer.getHostName(), defaultToken);
    }
    @Test
    public void getObjectOkTest() throws NoSuchFieldException, IOException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getObject())
        ));
        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);

        Mono<Object> responseObjectMono = clientDRS.getObject(1L);
        Object object = responseObjectMono.block();
        Assert.assertTrue( object instanceof Object);

    }

    @Test
    public void getObjectNotFoundTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("", HttpStatus.NOT_FOUND))));
        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));

        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseObjectMono = clientDRS.getObject(0L);
        try {
            responseObjectMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),404);
        }

    }

    @Test
    public void getObjectBadRequestTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));
        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));

        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseObjectMono = clientDRS.getObject(0L);
        try {
            responseObjectMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }

    @Test
    public void getBundleTest() throws com.sun.org.apache.xml.internal.utils.URI.MalformedURIException, NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getBundle())
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));

        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseBundleMono = clientDRS.getBundle(1L);
        Assert.assertTrue( responseBundleMono.block() instanceof Bundle);
    }

    @Test
    public void getBundleNotFoundTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.NOT_FOUND))
                        ));
        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseBundleMono = clientDRS.getBundle(0L);
        try {
            responseBundleMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),404);
        }
    }

    @Test
    public void getBundleBadRequestTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));

        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseBundleMono = clientDRS.getBundle(0L);
        try {
            responseBundleMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }

    @Test
    public void getAccessTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getAccessMethods())
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseAccessMethodsMono = clientDRS.getAccessMethod(1L, 1L);
        Assert.assertTrue( responseAccessMethodsMono.block() instanceof AccessMethods);
    }

    @Test
    public void getAccessNotFoundTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.NOT_FOUND))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseAccessMethodstMono = clientDRS.getAccessMethod(1L,1L);
        try {
            responseAccessMethodstMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),404);
        }
    }

    @Test
    public void getAccessBadRequestTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseAccessMethodstMono = clientDRS.getAccessMethod(0L,1L);
        try {
            responseAccessMethodstMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }







    @Test
    public void saveObjectOkTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getObject())
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));

        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        Assert.assertTrue(savedObjectMono.block() instanceof Object);


    }

    @Test
    public void saveObjectBadRequestTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        try {
            savedObjectMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }

    @Test
    public void saveBundleOkTest() throws NoSuchFieldException, com.sun.org.apache.xml.internal.utils.URI.MalformedURIException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getBundle())
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedBundleMono = clientDRS.saveBundle(Mono.just(TestObjectCreator.getBundle()));
        Assert.assertTrue(savedBundleMono.block() instanceof Bundle);
    }

    @Test
    public void saveBundleBadRequestTest() throws NoSuchFieldException, com.sun.org.apache.xml.internal.utils.URI.MalformedURIException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedBundleMono = clientDRS.saveBundle(Mono.just(TestObjectCreator.getBundle()));
        try {
            savedBundleMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }

    @Test
    public void saveAccessOkTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getAccessMethods())
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedAccessMono = clientDRS.saveAccessMethod(1L, Mono.just(TestObjectCreator.getAccessMethods()));
        Assert.assertTrue(savedAccessMono.block() instanceof AccessMethods);
    }

    @Test
    public void saveAccessBadRequestTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<AccessMethods> savedAccessMono = clientDRS.saveAccessMethod(1L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            savedAccessMono.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }

    @Test
    public void deleteObjectOkTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{}")
                        );

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

       Mono<Void> deleted =  clientDRS.deleteObject(0L);
       deleted.block();

    }

    @Test
    public void deleteObjectNotFoundTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.NOT_FOUND))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteObject(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),404);
        }
    }

    @Test
    public void deleteObjectBadRequestTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteObject(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }

    @Test
    public void deleteBundleOkTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{}")
                        );

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteBundle(0L);
        deleted.block();
    }

    @Test
    public void deleteBundleNotFoundTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.NOT_FOUND))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteBundle(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),404);
        }
    }

    @Test
    public void deleteBundleBadRequestTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteBundle(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }

    @Test
    public void deleteAccessOkTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{}")
                        );

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteAccessMethod(0L, 0L);
        deleted.block();
    }

    @Test
    public void deleteAccessNotFoundTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.NOT_FOUND))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteAccessMethod(0L, 0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),404);
        }
    }

    @Test
    public void deleteAccessBadRequestTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);
        Mono<Void> deleted =  clientDRS.deleteAccessMethod(0L, 0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }





    @Test
    public void updateObjectOkTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getObject())
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Object> updatedObject = clientDRS.updateObject(1L, Mono.just(TestObjectCreator.getObject()));
        Assert.assertTrue(updatedObject.block() instanceof  Object);
    }

    @Test
    public void updateObjectNotFoundTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.NOT_FOUND))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Object> updatedObject = clientDRS.updateObject(1L, Mono.just(TestObjectCreator.getObject()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),404);
        }
    }

    @Test
    public void updateObjectBadRequestTest() throws NoSuchFieldException, JsonProcessingException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Object> updatedObject = clientDRS.updateObject(1L, Mono.just(TestObjectCreator.getObject()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }


    @Test
    public void updateBundleOkTest() throws NoSuchFieldException, com.sun.org.apache.xml.internal.utils.URI.MalformedURIException, JsonProcessingException {


        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getBundle())
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Bundle> updatedBundle = clientDRS.updateBundle(1L, Mono.just(TestObjectCreator.getBundle()));
        Assert.assertTrue(updatedBundle.block() instanceof  Bundle);
    }

    @Test
    public void updateBundleNotFoundTest() throws NoSuchFieldException, com.sun.org.apache.xml.internal.utils.URI.MalformedURIException, JsonProcessingException {


        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.NOT_FOUND))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Bundle> updatedObject = clientDRS.updateBundle(1L, Mono.just(TestObjectCreator.getBundle()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),404);
        }
    }

    @Test
    public void updateBundleBadRequestTest() throws NoSuchFieldException, com.sun.org.apache.xml.internal.utils.URI.MalformedURIException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));




        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Bundle> updatedObject = clientDRS.updateBundle(1L, Mono.just(TestObjectCreator.getBundle()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }


    @Test
    public void updateAccessOkTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(TestObjectCreator.getAccessMethods())
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));



        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<AccessMethods> updatedAccess =  clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        Assert.assertTrue(updatedAccess.block() instanceof  AccessMethods);
    }

    @Test
    public void updateAccessNotFoundTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.NOT_FOUND))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));




        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<AccessMethods> updatedObject = clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
                updatedObject.block();

            throw new Error("", 500);
        }catch (Throwable error){
            Assert.assertEquals( ((Error)error.getCause()).getStatusCode(),404);
        }
    }

    @Test
    public void updateAccessBadRequestTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.BAD_REQUEST))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));




        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<AccessMethods> updatedObject = clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),400);
        }
    }



    @Test
    public void forbiddenTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.FORBIDDEN))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));




        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<AccessMethods> updatedObject = clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),403);
        }
    }

    @Test
    public void internalErrorTest() throws NoSuchFieldException, JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(new Error("",HttpStatus.INTERNAL_SERVER_ERROR))
                        ));

        WebClient webClient = WebClient.create((mockWebServer.url("/").toString()));


        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken("");
        Mono<AccessMethods> updatedObject = clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Throwable error){
            Assert.assertEquals(((Error)error.getCause()).getStatusCode(),500);
        }
    }



}
