package com.ega.datarepositorysevice.controller;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.utils.TestObjectCreator;
import com.sun.org.apache.xml.internal.utils.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;

@Import(Router.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class RouterPostTest {

    @Autowired
    AccessMethodsRepository accessMethodsRepository;

    @Autowired
    ObjectRepository objectRepository;

    @Autowired
    BundleRepository bundleRepository;

    @Autowired
    Router router;

    private AccessMethods accessMethods;
    private Object object;
    private Bundle bundle;
    private Bundle badBundle;
    private Object badObject;
    private AccessMethods badAccessMethods;

    private WebTestClient webTestClient;

    @Before
    public void prepareEnvironment() throws URI.MalformedURIException {
        webTestClient = WebTestClient.bindToRouterFunction(router.route()).build();

        object = TestObjectCreator.getObject();
        bundle = TestObjectCreator.getBundle();
        accessMethods = TestObjectCreator.getAccessMethods();
        accessMethods.setRegion("new region");

        badBundle = TestObjectCreator.getBundle();
        badBundle.setCreated(null);

        badObject = TestObjectCreator.getObject();
        badObject.setAccessMethods(new ArrayList<>());

        badAccessMethods = TestObjectCreator.getAccessMethods();
        badAccessMethods.setAccessURL(null);


    }

    @Test
    public void objectSaveCreatedTest() {
        webTestClient.post()
                .uri("/objects")
                .body(BodyInserters.fromObject(object))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Object.class).isEqualTo(object);
    }

    @Test
    public void objectSaveEmptyBodyTest() {
        Error error = webTestClient.post()
                .uri("/objects")
                .body(BodyInserters.empty())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: REQUEST BODY IS EMPTY", HttpStatus.BAD_REQUEST), error);
    }

    @Test
    public void objectSaveBadBodyTest() {
        Error error = webTestClient.post()
                .uri("/objects")
                .body(BodyInserters.fromObject(badObject))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: ERROR IN ATTRIBUTE: ACCESSMETHODS, ERROR VALUE: [], ERROR CAUSE: ACCESSMETHODS MUST CONTAINS AT LEAST ONE ELEMENT \n" +
                "\n", HttpStatus.BAD_REQUEST), error);

    }


    @Test
    public void bundleSaveCreatedTest() {
        webTestClient.post()
                .uri("/bundles")
                .body(BodyInserters.fromObject(bundle))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Bundle.class).isEqualTo(bundle);
    }

    @Test
    public void bundleSaveEmptyBodyTest() {
        Error error = webTestClient.post()
                .uri("/bundles")
                .body(BodyInserters.empty())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: REQUEST BODY IS EMPTY", HttpStatus.BAD_REQUEST), error);

    }

    @Test
    public void bundleSaveBadBodyTest() {
        Error error = webTestClient.post()
                .uri("/bundles")
                .body(BodyInserters.fromObject(badBundle))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: ERROR IN ATTRIBUTE: CREATED, ERROR VALUE: NULL, ERROR CAUSE: CREATED MUST NOT BE NULL \n" +
                "\n", HttpStatus.BAD_REQUEST), error);

    }


    @Test
    public void accessSaveCreatedTest() {
        Object savedObject = objectRepository.save(TestObjectCreator.getObject());
        webTestClient.post()
                .uri(String.format("/objects/%d/access", savedObject.getId()))
                .body(BodyInserters.fromObject(accessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccessMethods.class).isEqualTo(accessMethods);
    }


    @Test
    public void accessSaveBadBodyTest() {
        Object savedObject = objectRepository.save(TestObjectCreator.getObject());
        Error error = webTestClient.post()
                .uri(String.format("/objects/%d/access", savedObject.getId()))
                .body(BodyInserters.fromObject(badAccessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: ERROR IN ATTRIBUTE: ACCESSURL, ERROR VALUE: NULL, ERROR CAUSE: ACCESSURL MUST NOT BE NULL \n", HttpStatus.BAD_REQUEST), error);

    }

    @Test
    public void accessSaveEmptyBodyTest() {
        Object savedObject = objectRepository.save(TestObjectCreator.getObject());

        Error error = webTestClient.post()
                .uri(String.format("/objects/%d/access", savedObject.getId()))
                .body(BodyInserters.empty())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: REQUEST BODY IS EMPTY", HttpStatus.BAD_REQUEST), error);

    }

    @Test
    public void accessSaveObjectNotFoundTest() {
        Object savedObject = objectRepository.save(TestObjectCreator.getObject());
        Error error = webTestClient.post()
                .uri(String.format("/objects/%s/access", "0"))
                .body(BodyInserters.fromObject(accessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: OBJECT NOT FOUND", HttpStatus.NOT_FOUND), error);

    }


    @Test
    public void accessSaveObjectBadParameterTest() {
        Error error = webTestClient.post()
                .uri(String.format("/objects/%s/access", "id"))
                .body(BodyInserters.fromObject(accessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);

    }


}
