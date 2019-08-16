package com.ega.datarepositorysevice.controller;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.model.Error;

import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.utils.TestObjectCreator;
import com.sun.org.apache.xml.internal.utils.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Import(Router.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class RouterDeleteTest {
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

    private WebTestClient webTestClient;

    @Before
    public void prepareEnvironment() throws URI.MalformedURIException {
        webTestClient = WebTestClient.bindToRouterFunction(router.route()).build();

        object = TestObjectCreator.getObject();
        bundle = TestObjectCreator.getBundle();

        object = objectRepository.save(object);
        bundle = bundleRepository.save(bundle);
        accessMethods = object.getAccessMethods().get(0);

    }

    @Test
    public void objectDeleteOkTest() {
        webTestClient.delete()
                .uri(String.format("/objects/%s", object.getId().toString()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

    }

    @Test
    public void objectDeleteIllegalParameterTest() {
        Error error = webTestClient.delete()
                .uri(String.format("/objects/%s", "id"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);
    }

    @Test
    public void objectDeleteEmptyParameterTest() {
        webTestClient.delete()
                .uri("/objects/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    @Test
    public void objectDeleteNotFoundTest() {
        Error error = webTestClient.delete()
                .uri(String.format("/objects/%s", "0"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: NO CLASS COM.EGA.DATAREPOSITORYSEVICE.MODEL.OBJECT ENTITY WITH ID 0 EXISTS!", HttpStatus.NOT_FOUND), error);
    }


    @Test
    public void bundleDeleteOkTest() {
        webTestClient.delete()
                .uri(String.format("/bundles/%d", bundle.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    public void bundleDeleteIllegalParameterTest() {
        Error error = webTestClient.delete()
                .uri(String.format("/bundles/%s", "id"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class)
                .returnResult()
                .getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);
    }

    @Test
    public void bundleDeleteEmptyParameterTest() {
        webTestClient.delete()
                .uri("/bundles/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    @Test
    public void bundleDeleteNotFoundTest() {
        Error error = webTestClient.delete()
                .uri(String.format("/bundles/%s", "0"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class)
                .returnResult()
                .getResponseBody();
        Assert.assertEquals(new Error(" Reason: NO CLASS COM.EGA.DATAREPOSITORYSEVICE.MODEL.BUNDLE ENTITY WITH ID 0 EXISTS!", HttpStatus.NOT_FOUND), error);
    }


    @Test
    public void accessDeleteOkTest() {
        webTestClient.delete()
                .uri(String.format("/objects/%d/access/%d", object.getId(), accessMethods.getAccessId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    public void accessDeleteIllegalAccessParameterTest() {
        Error error = webTestClient.delete()
                .uri(String.format("/objects/%d/access/%s", object.getId(), "id"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class)
                .returnResult()
                .getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);
    }

    @Test
    public void accessDeleteIllegalObjectParameterTest() {
        Error error = webTestClient.delete()
                .uri(String.format("/objects/%s/access/%d", "id", accessMethods.getAccessId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class)
                .returnResult()
                .getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);
    }

    private static final Logger LOGGER = LogManager.getLogger(RouterDeleteTest.class);

    @Test
    public void accessDeleteEmptyObjectParameterTest() {
        webTestClient.delete()
                .uri(String.format("/objects//access/%d", accessMethods.getAccessId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
        //.isEqualTo(new Error("\"The provided id parameter of object is empty\"", HttpStatus.BAD_REQUEST));
        //Assert.assertEquals(new Error("\"The provided id parameter of object is empty\"", HttpStatus.BAD_REQUEST), error);
    }

    @Test
    public void accessDeleteEmptyAccessParameterTest() {
        webTestClient.delete()
                .uri(String.format("/objects/%d/access/", object.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();

    }

    @Test
    public void accessDeleteAccessNotFoundTest() {
        Error error = webTestClient.delete()
                .uri(String.format("/objects/%d/access/%s", object.getId(), "0"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class)
                .returnResult()
                .getResponseBody();
        Assert.assertEquals(new Error(" Reason: ID NOT FOUND", HttpStatus.NOT_FOUND), error);
    }

    @Test
    public void accessDeleteObjectNotFoundTest() {
        Error error = webTestClient.delete()
                .uri(String.format("/objects/%s/access/%d", "0", accessMethods.getAccessId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class)
                .returnResult()
                .getResponseBody();
        Assert.assertEquals(new Error(" Reason: ID OF OBJECT IS NOT FOUND", HttpStatus.NOT_FOUND), error);
    }
}
