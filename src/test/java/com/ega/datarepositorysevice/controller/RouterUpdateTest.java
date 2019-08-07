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
public class RouterUpdateTest {

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
    private Object newObject;
    private Bundle newBundle;
    private AccessMethods newAccessMethods;
    private Object badObject;
    private Bundle badBundle;
    private AccessMethods badAccessMethods;

    private WebTestClient webTestClient;

    @Before
    public void prepareEnvironment() throws URI.MalformedURIException {
        webTestClient = WebTestClient.bindToRouterFunction(router.route()).build();

        object = TestObjectCreator.getObject();
        bundle = TestObjectCreator.getBundle();

        object = objectRepository.save(object);
        bundle = bundleRepository.save(bundle);
        accessMethods = object.getAccessMethods().get(0);

        newObject = TestObjectCreator.getObject();
        newObject.setId(object.getId());
        newObject.setName("new Name");
        newBundle = bundle;
        newBundle.setId(bundle.getId());
        newBundle.setName("new Name");
        newAccessMethods = TestObjectCreator.getAccessMethods();
        newAccessMethods.setAccessId(accessMethods.getAccessId());
        newAccessMethods.setRegion("new region");

        badAccessMethods = TestObjectCreator.getAccessMethods();
        badAccessMethods.setAccessId(accessMethods.getAccessId());
        badBundle = TestObjectCreator.getBundle();
        badObject = TestObjectCreator.getObject();
        badAccessMethods.setType(null);
        badBundle.setCreated(null);
        badObject.setAccessMethods(new ArrayList<>());
    }

    @Test
    public void updateObjectOk() {
        webTestClient.put()
                .uri(String.format("/objects/%d", object.getId()))
                .body(BodyInserters.fromObject(newObject))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Object.class).isEqualTo(newObject);
    }

    @Test
    public void updateObjectBadBody() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%d", object.getId()))
                .body(BodyInserters.fromObject(badObject))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();//.isEqualTo(new Error("", HttpStatus.BAD_REQUEST));
        Assert.assertEquals(new Error("The request is malformed. Reason: ERROR IN ATTRIBUTE: ACCESSMETHODS, ERROR VALUE: [], ERROR CAUSE: ACCESSMETHODS MUST CONTAINS AT LEAST ONE ELEMENT \n" +
                "\n", HttpStatus.BAD_REQUEST), error);
    }

    @Test
    public void updateObjectEmptyBody() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%d", object.getId()))
                .body(BodyInserters.empty())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: REQUEST BODY IS EMPTY", HttpStatus.BAD_REQUEST), error);

    }

    @Test
    public void updateObjectNotFound() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%s", "0"))
                .body(BodyInserters.fromObject(newObject))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: ID OF OBJECT IS NOT FOUND", HttpStatus.NOT_FOUND), error);

    }

    @Test
    public void updateObjectBadId() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%s", "id"))
                .body(BodyInserters.fromObject(newObject))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);

    }


    @Test
    public void updateBundleOk() {
        webTestClient.put()
                .uri(String.format("/bundles/%d", bundle.getId()))
                .body(BodyInserters.fromObject(newBundle))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bundle.class).isEqualTo(newBundle);
    }

    @Test
    public void updateBundleBadBody() {
        Error error = webTestClient.put()
                .uri(String.format("/bundles/%d", bundle.getId()))
                .body(BodyInserters.fromObject(badBundle))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: ERROR IN ATTRIBUTE: CREATED, ERROR VALUE: NULL, ERROR CAUSE: CREATED MUST NOT BE NULL \n" +
                "\n", HttpStatus.BAD_REQUEST), error);

    }

    @Test
    public void updateBundleEmptyBody() {
        Error error = webTestClient.put()
                .uri(String.format("/bundles/%d", bundle.getId()))
                .body(BodyInserters.empty())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: BODY IS EMPTY", HttpStatus.BAD_REQUEST), error);

    }


    @Test
    public void updateBundleNotFound() {
        Error error = webTestClient.put()
                .uri(String.format("/bundles/%d", 0L))
                .body(BodyInserters.fromObject(newBundle))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();

        Assert.assertEquals(new Error(" Reason: ID OF BUNDLE IS NOT FOUND", HttpStatus.NOT_FOUND), error);

    }

    @Test
    public void updateBundleBadId() {
        Error error = webTestClient.put()
                .uri(String.format("/bundles/%s", "id"))
                .body(BodyInserters.fromObject(newBundle))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);

    }


    @Test
    public void updateAccessOk() {
        webTestClient.put()
                .uri(String.format("/objects/%d/access/%d", object.getId(), newAccessMethods.getAccessId()))
                .body(BodyInserters.fromObject(newAccessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccessMethods.class).isEqualTo(newAccessMethods);

    }


    @Test
    public void updateAccessBadBody() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%d/access/%d", object.getId(), newAccessMethods.getAccessId()))
                .body(BodyInserters.fromObject(badAccessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: ERROR IN ATTRIBUTE: TYPE, ERROR VALUE: NULL, ERROR CAUSE: TYPE MUST NOT BE NULL \n", HttpStatus.BAD_REQUEST), error);

    }

    @Test
    public void updateAccessEmptyBody() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%d/access/%d", object.getId(), accessMethods.getAccessId()))
                .body(BodyInserters.empty())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: REQUEST BODY IS EMPTY", HttpStatus.BAD_REQUEST), error);

    }

    @Test
    public void updateAccessNotFound() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%d/access/%s", object.getId(), "0"))
                .body(BodyInserters.fromObject(accessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: ACCESS ID NOT FOUND", HttpStatus.NOT_FOUND), error);

    }

    @Test
    public void updateAccessObjectParameterNotFound() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%s/access/%d", "0", accessMethods.getAccessId()))
                .body(BodyInserters.fromObject(accessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error(" Reason: OBJECT BY GIVEN ID NOT FOUND", HttpStatus.NOT_FOUND), error);

    }

    @Test
    public void updateAccessObjectBadId() {
        Error error = webTestClient.put()
                .uri(String.format("/objects/%s/access/%d", "id", accessMethods.getAccessId()))
                .body(BodyInserters.fromObject(accessMethods))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class).returnResult().getResponseBody();
        Assert.assertEquals(new Error("The request is malformed. Reason: FOR INPUT STRING: \"ID\"", HttpStatus.BAD_REQUEST), error);

    }
}
