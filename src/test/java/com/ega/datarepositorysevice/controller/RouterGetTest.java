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


import java.io.IOException;

@Import(Router.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class RouterGetTest {

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
        accessMethods = object.getAccessMethods().get(0);
        bundle = bundleRepository.save(bundle);
    }


    @Test
    public void testAccessMethodsPathOk() {
        webTestClient.get()
                .uri(String.format("/objects/%d/access/%d", object.getId(), accessMethods.getAccessId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccessMethods.class).isEqualTo(accessMethods);
    }

    @Test
    public void testAccessMethodsPathNotFound() {
        Error error = webTestClient.get()
                .uri(String.format("/objects/%s/access/%s", object.getId(), "0"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: ACCESS ID NOT FOUND", HttpStatus.NOT_FOUND), error);
    }

    @Test
    public void testAccessMethodsObjectPathNotFound() {
        Error error = webTestClient.get()
                .uri(String.format("/objects/%s/access/%s", "0", accessMethods.getAccessId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: OBJECT BY GIVEN ID NOT FOUND", HttpStatus.NOT_FOUND), error);
    }

    @Test
    public void testAccessMethodsObjectPathBad() {
        Error error = webTestClient.get()
                .uri(String.format("/objects/%s/access/%s", "id", accessMethods.getAccessId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);
    }

    @Test
    public void testAccessMethodsPathBad() {
        Error error = webTestClient.get()
                .uri(String.format("/objects/%s/access/%s", object.getId(), "id"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);
    }

    @Test
    public void testObjectPathOk() {

        webTestClient.get()
                .uri(String.format("/objects/%s", object.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Object.class).isEqualTo(object);
    }

    @Test
    public void testObjectPathNotFound() {
        Error error = webTestClient.get()
                .uri(String.format("/objects/%d", 0))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: ID OF OBJECT IS NOT FOUND", HttpStatus.NOT_FOUND), error);

    }

    @Test
    public void testObjectPathBad() {
        Error error = webTestClient.get()
                .uri(String.format("/objects/%s", "id"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);

    }

    @Test
    public void testBundlePathOk() {
        webTestClient.get()
                .uri(String.format("/bundles/%d", bundle.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bundle.class).isEqualTo(bundle);

    }

    @Test
    public void testBundlePathNotFound() {
        Error error = webTestClient.get()
                .uri(String.format("/bundles/%d", 0))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: ID OF BUNDLE IS NOT FOUND", HttpStatus.NOT_FOUND), error);

    }

    @Test
    public void testBundlePathBad() {
        Error error = webTestClient.get()
                .uri(String.format("/bundles/%s", "id"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);

    }


    @Test
    public void serviceInfoTest() {
        webTestClient.get()
                .uri("/service-info")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().json("{\n" +
                "  \"version\": \"1.0\",\n" +
                "  \"title\": \"DRS service\",\n" +
                "  \"description\": \"GSOC 2019 DRS project\",\n" +
                "  \"contact\": {},\n" +
                "  \"license\": {}\n" +
                "}");
    }

}
