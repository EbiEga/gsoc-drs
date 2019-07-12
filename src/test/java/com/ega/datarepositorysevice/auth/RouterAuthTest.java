package com.ega.datarepositorysevice.auth;

import com.ega.datarepositorysevice.WebSecurityConfig;
import com.ega.datarepositorysevice.controller.Router;
import com.ega.datarepositorysevice.controller.handler.AccessMethodHandler;
import com.ega.datarepositorysevice.controller.handler.BundleHandler;
import com.ega.datarepositorysevice.controller.handler.ObjectHandler;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import com.ega.datarepositorysevice.service.BundleService;
import com.ega.datarepositorysevice.service.ObjectService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@ContextConfiguration(classes = {Router.class, AccessMethodHandler.class, ObjectHandler.class, BundleHandler.class, WebSecurityConfig.class})
@ActiveProfiles({"dev", "prod"})
@WebFluxTest
public class RouterAuthTest {
    @Autowired
    private ApplicationContext context;

    @MockBean
    ObjectService objectService;
    @MockBean
    BundleService bundleService;
    @MockBean
    AccessMethodsService accessMethodsService;


    @Value("${security.oauth2.client.client-id}")
    String clientId;

    @Value("${security.oauth2.client.client-secret}")
    String clientSecret;


    private WebTestClient webTestClient;

    @Before
    public void prepareEnvironment() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
        when(objectService.getObjectById(1L)).thenReturn(Mono.empty());
        when(accessMethodsService.getAccessMethodsById(1L)).thenReturn(Mono.empty());
        when(bundleService.getBundleById(1L)).thenReturn(Mono.empty());
    }


    @Test
    public void testAccessMethodsPathOk() {
        webTestClient.get()
                .uri("/objects/1/access/1")
                .headers(h -> h.setBearerAuth(getToken("amp-dev@ebi.ac.uk", "dN9yCSbQ")))
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    public void testAccessMethodsPathUnauthorized() {
        webTestClient.get()
                .uri("/objects/1/access/1")
                .headers(h -> h.setBearerAuth("r"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();

    }


    @Test
    public void testObjectPathOk() {

        webTestClient.get()
                .uri("/objects/1")
                .headers(h -> h.setBearerAuth(getToken("amp-dev@ebi.ac.uk", "dN9yCSbQ")))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testObjectPathUnauthorized() {

        webTestClient.get()
                .uri("/objects/1")
                .headers(h -> h.setBearerAuth("r"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }


    @Test
    public void testBundlePathOk() {
        webTestClient.get()
                .uri("/bundles/1")
                .headers(h -> h.setBearerAuth(getToken("amp-dev@ebi.ac.uk", "dN9yCSbQ")))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testBundlePathUnauthorized() {
        webTestClient.get()
                .uri("/bundles/1")
                .headers(h -> h.setBearerAuth("r"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }


    @Test
    public void testBadRequest() {
        webTestClient.get()
                .uri(String.format("/bundles/%s", "qeqw=2131"))
                .accept()
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void testServiceInfoOk() {

        webTestClient.get()
                .uri("/service-info")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }


    private String getToken(String username, String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);
        params.add("scope", "openid email");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        String resultString = restTemplate.postForObject("https://ega.ebi.ac.uk:8443/ega-openid-connect-server/token", request, String.class);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }


}


