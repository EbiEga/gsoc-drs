package com.ega.datarepositorysevice.controller;

import com.ega.datarepositorysevice.controller.handler.AccessMethodHandler;
import com.ega.datarepositorysevice.controller.handler.BundleHandler;
import com.ega.datarepositorysevice.controller.handler.ObjectHandler;
import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.repository.BundleRepository;
import com.ega.datarepositorysevice.repository.ObjectRepository;
//import com.ega.datarepositorysevice.utils.TestObjectCreator;
import com.ega.datarepositorysevice.utils.TestObjectCreator;
import com.sun.org.apache.xml.internal.utils.URI;

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
import org.springframework.web.reactive.function.server.ServerRequest;


import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Import(Router.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class RouterTest {

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

        accessMethods = TestObjectCreator.getAccessMethods();
        object = TestObjectCreator.getObject();
        bundle = TestObjectCreator.getBundle();

        accessMethods = accessMethodsRepository.save(accessMethods);
        object = objectRepository.save(object);
        bundle = bundleRepository.save(bundle);
    }


    @Test
    public void testAccessMethodsPathOk() throws IOException {
        webTestClient.get()
                .uri(String.format("/objects/%d/access/%d", object.getId(), accessMethods.getAccessId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccessMethods.class).isEqualTo(accessMethods);
    }

    @Test
    public void testAccessMethodsPathNotFound() {
        webTestClient.get()
                .uri(String.format("/objects/%s/access/%s", "0", "0"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testObjectPathOk() throws IOException {

        webTestClient.get()
                .uri(String.format("/objects/%s", object.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Object.class).isEqualTo(object);
    }

    @Test
    public void testObjectPathNotFound() {
        webTestClient.get()
                .uri(String.format("/objects/%d", 0))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testBundlePathOk() throws IOException {
        webTestClient.get()
                .uri(String.format("/bundles/%d", bundle.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bundle.class).isEqualTo(bundle);
    }

    @Test
    public void testBundlePathNotFound() {
        webTestClient.get()
                .uri(String.format("/bundles/%d", 0))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testBadRequest() {
        webTestClient.get()
                .uri(String.format("/bundles/%s", "qeqw=2131"))
                .accept()
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("status_code", 400);
    }

    @Test
    public void testNotFoundEmpty() {
        webTestClient.get()
                .uri(String.format("/bundles/%s", ""))
                .accept()
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("status_code", 404);
    }

    @Test
    public void testNotFound() {
        webTestClient.get()
                .uri(String.format("/bundles/%s", "34"))
                .accept()
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("status_code", 404);
    }

    @Test
    public void testInternalServerError() {

        BundleHandler bundleHandler = mock(BundleHandler.class);
        ObjectHandler objectHandler = mock(ObjectHandler.class);
        AccessMethodHandler accessMethodHandler = mock(AccessMethodHandler.class);
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("bundle_id")).thenReturn("1");
        when(accessMethodHandler.getAccess(request)).thenThrow(IllegalStateException.class);
        WebTestClient webTestClient = WebTestClient
                .bindToRouterFunction(new Router(objectHandler, bundleHandler, accessMethodHandler).route())
                .build();
        webTestClient.get()
                .uri(String.format("/bundles/%d", 1))
                .accept()
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody().jsonPath("status_code", 500);
        Error error = webTestClient.get()
                .uri(String.format("/bundles/%d", 1))
                .accept()
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(Error.class).returnResult().getResponseBody();
        System.out.println();
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
