import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class ClientDRS {

    private WebClient restTemplate = WebClient.create() ;
    private String drsServerUri;
    private String token = "";

    public ClientDRS(String drsServerHost){
        drsServerUri = drsServerHost;
    }

    public ClientDRS(String drsServerHost, String bearerToken){
        drsServerUri = drsServerHost;
        token =  bearerToken;
    }
    
    public Mono<Object> getObject(Long objectId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();
        
        return responseMono.flatMap(response-> handleResponse(response, Object.class));
    }

    public Mono<Bundle> getBundle(Long bundleId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/bundles/{bundle_id}").build(bundleId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        return responseMono.flatMap(response-> handleResponse(response, Bundle.class));

    }

    public Mono<AccessMethods> getAccessMethod(Long objectId, Long accessId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}/access/{access_id}").build(objectId, accessId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, AccessMethods.class));

    }




    public Mono<Object> saveObject(Mono<Object> object){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(object, Object.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Object.class));
    }

    public Mono<Bundle> saveBundle(Mono<Bundle> bundle){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/bundles").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(bundle, Bundle.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Bundle.class));
    }

    public Mono<AccessMethods> saveAccessMethod(Long objectId,Mono<AccessMethods> accessMethods){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}/access").build(objectId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(accessMethods, AccessMethods.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, AccessMethods.class));
    }


    public Mono<Void> deleteObject(Long objectId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}").build(objectId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();
        return responseMono.flatMap(response->handleResponse(response, Void.class));
    }

    public Mono<Void> deleteBundle(Long bundleId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/bundles/{bundle_id}").build(bundleId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Void.class));
    }

    public Mono<Void> deleteAccessMethod(Long objectId, Long accessId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}/access/{access_id}").build(objectId, accessId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Void.class));
    }


    public Mono<Object> updateObject(Long objectId,Mono<Object> object){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}").build(objectId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(object, Object.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Object.class));
    }

    public Mono<Bundle> updateBundle(Long bundleId,Mono<Bundle> bundle){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/bundles/{bundle_id}").build(bundleId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(bundle, Bundle.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Bundle.class));
    }

    public Mono<AccessMethods> updateAccessMethod(Long objectId,Long accessId,Mono<AccessMethods> accessMethods){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}/access/{access_id}").build(objectId, accessId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(accessMethods, AccessMethods.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, AccessMethods.class));
    }

    public void updateAccessToken(String token){
        this.token = token;
    }

    private <T> Mono<T> handleResponse(ClientResponse response,Class<? extends T> aClass){
            switch (response.statusCode()) {
                case OK:
                case CREATED:
                    return response.bodyToMono(aClass);
                case NOT_FOUND:
                case BAD_REQUEST:
                case INTERNAL_SERVER_ERROR:
                case FORBIDDEN:
                    return response
                            .bodyToMono(Error.class)
                            .flatMap(Mono::error);
                default:
                    return Mono.error(new Error("Unsupported error", HttpStatus.INTERNAL_SERVER_ERROR));
            }

    }
}
