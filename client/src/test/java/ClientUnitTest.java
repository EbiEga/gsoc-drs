import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import com.sun.org.apache.xml.internal.utils.URI;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.FieldSetter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientUnitTest {
    private String host = "";

    private ClientDRS clientDRS;
    private String defaultToken = "token";
    private String defaultHost = "host";


    @Test
    public void getObjectOkTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK,TestObjectCreator.getObject()));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseObjectMono = clientDRS.getObject(1L);
        Assert.assertTrue( responseObjectMono.block() instanceof Object);

    }

    @Test
    public void getObjectNotFoundTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, new Error("",HttpStatus.NOT_FOUND)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseObjectMono = clientDRS.getObject(0L);
        try {
            responseObjectMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }

    }

    @Test
    public void getObjectBadRequestTest() throws NoSuchFieldException {

        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, new Error("",HttpStatus.BAD_REQUEST)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseObjectMono = clientDRS.getObject(0L);
        try {
            responseObjectMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }    }

    @Test
    public void getBundleTest() throws URI.MalformedURIException, NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, TestObjectCreator.getBundle()));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseBundleMono = clientDRS.getBundle(1L);
        Assert.assertTrue( responseBundleMono.block() instanceof Bundle);
    }

    @Test
    public void getBundleNotFoundTest() throws NoSuchFieldException {

        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, new Error("",HttpStatus.NOT_FOUND)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseBundleMono = clientDRS.getBundle(0L);
        try {
            responseBundleMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }    }

    @Test
    public void getBundleBadRequestTest() throws NoSuchFieldException {

        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, new Error("",HttpStatus.BAD_REQUEST)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseBundleMono = clientDRS.getBundle(0L);
        try {
            responseBundleMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }       }

    @Test
    public void getAccessTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1/access/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, TestObjectCreator.getAccessMethods()));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseAccessMethodsMono = clientDRS.getAccessMethod(1L, 1L);
        Assert.assertTrue( responseAccessMethodsMono.block() instanceof AccessMethods);
    }

    @Test
    public void getAccessNotFoundTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1/access/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus. NOT_FOUND, new Error("",HttpStatus.NOT_FOUND)));

        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseAccessMethodstMono = clientDRS.getAccessMethod(1L,1L);
        try {
            responseAccessMethodstMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }
    }

    @Test
    public void getAccessBadRequestTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1/access/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, new Error("",HttpStatus.BAD_REQUEST)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono responseAccessMethodstMono = clientDRS.getAccessMethod(0L,1L);
        try {
            responseAccessMethodstMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }







    @Test
    public void saveObjectOkTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(TestObjectCreator.getObject()))
                .exchange()).thenReturn(createClientResponse(HttpStatus.CREATED, TestObjectCreator.getObject() ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        Assert.assertTrue(savedObjectMono.block() instanceof Object);


    }

    @Test
    public void saveObjectBadRequestTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(TestObjectCreator.getObject()))
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, new Error("", HttpStatus.BAD_REQUEST)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        try {
            savedObjectMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void saveBundleOkTest() throws URI.MalformedURIException, NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(TestObjectCreator.getObject()))
                .exchange()).thenReturn(createClientResponse(HttpStatus.CREATED, TestObjectCreator.getObject() ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedBundleMono = clientDRS.saveBundle(Mono.just(TestObjectCreator.getBundle()));
        Assert.assertTrue(savedBundleMono.block() instanceof Bundle);
    }

    @Test
    public void saveBundleBadRequestTest() throws NoSuchFieldException, URI.MalformedURIException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(TestObjectCreator.getObject()))
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, new Error("", HttpStatus.BAD_REQUEST)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedBundleMono = clientDRS.saveBundle(Mono.just(TestObjectCreator.getBundle()));
        try {
            savedBundleMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void saveAccessOkTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1/access").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(TestObjectCreator.getObject()))
                .exchange()).thenReturn(createClientResponse(HttpStatus.CREATED, TestObjectCreator.getAccessMethods()));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono savedAccessMono = clientDRS.saveAccessMethod(1L, Mono.just(TestObjectCreator.getAccessMethods()));
        Assert.assertTrue(savedAccessMono.block() instanceof AccessMethods);
    }

    @Test
    public void saveAccessBadRequestTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1/access").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(TestObjectCreator.getAccessMethods()))
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, TestObjectCreator.getAccessMethods()));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<AccessMethods> savedAccessMono = clientDRS.saveAccessMethod(1L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            savedAccessMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void deleteObjectOkTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, null ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

       Mono<Void> deleted =  clientDRS.deleteObject(0L);
       deleted.block();

    }

    @Test
    public void deleteObjectNotFoundTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, null ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteObject(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }
    }

    @Test
    public void deleteObjectBadRequestTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, null ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteObject(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void deleteBundleOkTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, null ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteBundle(0L);
        deleted.block();
    }

    @Test
    public void deleteBundleNotFoundTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, null ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteBundle(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }
    }

    @Test
    public void deleteBundleBadRequestTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, null ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteBundle(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void deleteAccessOkTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0/access/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, null ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteAccessMethod(0L, 0L);
        deleted.block();
    }

    @Test
    public void deleteAccessNotFoundTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0/access/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, new Error("", HttpStatus.NOT_FOUND)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);

        Mono<Void> deleted =  clientDRS.deleteAccessMethod(0L, 0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }
    }

    @Test
    public void deleteAccessBadRequestTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0/access/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, new Error("", HttpStatus.BAD_REQUEST)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);
        clientDRS.updateAccessToken(defaultToken);
        Mono<Void> deleted =  clientDRS.deleteAccessMethod(0L, 0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }





    @Test
    public void updateObjectOkTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getObject()), Object.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, TestObjectCreator.getObject() ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Object> updatedObject = clientDRS.updateObject(1L, Mono.just(TestObjectCreator.getObject()));
        Assert.assertTrue(updatedObject.block() instanceof  Object);
    }

    @Test
    public void updateObjectNotFoundTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getObject()), Object.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, new Error("", HttpStatus.NOT_FOUND)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Object> updatedObject = clientDRS.updateObject(1L, Mono.just(TestObjectCreator.getObject()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }
    }

    @Test
    public void updateObjectBadRequestTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getObject()), Object.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, new Error("", HttpStatus.NOT_FOUND)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Object> updatedObject = clientDRS.updateObject(1L, Mono.just(TestObjectCreator.getObject()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }


    @Test
    public void updateBundleOkTest() throws NoSuchFieldException, URI.MalformedURIException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getBundle()), Bundle.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, TestObjectCreator.getBundle() ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Bundle> updatedBundle = clientDRS.updateBundle(1L, Mono.just(TestObjectCreator.getBundle()));
        Assert.assertTrue(updatedBundle.block() instanceof  Bundle);
    }

    @Test
    public void updateBundleNotFoundTest() throws NoSuchFieldException, URI.MalformedURIException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getBundle()), Bundle.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, TestObjectCreator.getBundle() ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Bundle> updatedObject = clientDRS.updateBundle(1L, Mono.just(TestObjectCreator.getBundle()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }
    }

    @Test
    public void updateBundleBadRequestTest() throws NoSuchFieldException, URI.MalformedURIException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/bundles/1").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getObject()), Object.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, TestObjectCreator.getBundle() ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<Bundle> updatedObject = clientDRS.updateBundle(1L, Mono.just(TestObjectCreator.getBundle()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }


    @Test
    public void updateAccessOkTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0/access/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getAccessMethods()), AccessMethods.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.OK, TestObjectCreator.getAccessMethods() ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<AccessMethods> updatedAccess =  clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        Assert.assertTrue(updatedAccess.block() instanceof  AccessMethods);
    }

    @Test
    public void updateAccessNotFoundTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0/access/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getObject()), Object.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.NOT_FOUND, new Error("", HttpStatus.NOT_FOUND) ));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<AccessMethods> updatedObject = clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }
    }

    @Test
    public void updateAccessBadRequestTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0/access/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(defaultToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getAccessMethods()), AccessMethods.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.BAD_REQUEST, new Error("", HttpStatus.BAD_REQUEST)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<AccessMethods> updatedObject = clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }



    @Test
    public void forbiddenTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0/access/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(""))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getAccessMethods()), AccessMethods.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.FORBIDDEN, new Error("", HttpStatus.FORBIDDEN)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken(defaultToken);
        Mono<AccessMethods> updatedObject = clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),403);
        }
    }

    @Test
    public void internalErrorTest() throws NoSuchFieldException {
        WebClient webClient = mock(WebClient.class);
        when(webClient
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(defaultHost).path("/objects/0/access/0").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(""))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(TestObjectCreator.getAccessMethods()), AccessMethods.class))
                .exchange()).thenReturn(createClientResponse(HttpStatus.INTERNAL_SERVER_ERROR, new Error("", HttpStatus.INTERNAL_SERVER_ERROR)));
        FieldSetter.setField(clientDRS, clientDRS.getClass().getDeclaredField("restTemplate"), webClient);

        clientDRS.updateAccessToken("");
        Mono<AccessMethods> updatedObject = clientDRS.updateAccessMethod(0L,0L, Mono.just(TestObjectCreator.getAccessMethods()));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),500);
        }
    }



    private <T> Mono<ClientResponse> createClientResponse(HttpStatus status, T entity){
        ClientResponse clientResponse = mock(ClientResponse.class);
        Mono mono;
        if(entity instanceof Error) {
             mono = Mono.error((Error)entity);
        }else if (entity == null){
            mono = Mono.empty();
        }
        else  {
            mono = Mono.just(entity);

        }
        when(clientResponse.bodyToMono(entity.getClass())).thenReturn(mono);
        when(clientResponse.statusCode()).thenReturn(status);
        return Mono.just(clientResponse);
    }

}
