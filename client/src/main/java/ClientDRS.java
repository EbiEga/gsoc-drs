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

    public ClientDRS(String drsServerHost){
        drsServerUri = drsServerHost;
    }
    
    Mono<Object> getObject(Long objectId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();
        
        return responseMono.flatMap(response-> handleResponse(response, Object.class));
    }

    Mono<Bundle> getBundle(Long bundleId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/bundles/{bundle_id}").build(bundleId))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        return responseMono.flatMap(response-> handleResponse(response, Bundle.class));

    }

    Mono<AccessMethods> getAccessMethod(Long objectId, Long accessId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}/access/{access_id}").build(objectId, accessId))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, AccessMethods.class));

    }




    Mono<Object> saveObject(Mono<Object> object){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects").build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(object, Object.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Object.class));
    }

    Mono<Bundle> saveBundle(Mono<Bundle> bundle){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/bundles").build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(bundle, Bundle.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Bundle.class));
    }

    Mono<AccessMethods> saveAccessMethod(Long objectId,Mono<AccessMethods> accessMethods){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}/access").build(objectId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(accessMethods, AccessMethods.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, AccessMethods.class));
    }


    Mono<Void> deleteObject(Long objectId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}").build(objectId))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();
        return responseMono.flatMap(response->handleResponse(response, Void.class));
    }

    Mono<Void> deleteBundle(Long bundleId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/bundles/{bundle_id}").build(bundleId))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Void.class));
    }

    Mono<Void> deleteAccessMethod(Long objectId, Long accessId){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}/access/{access_id}").build(objectId, accessId))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Void.class));
    }


    Mono<Object> updateObject(Long objectId,Mono<Object> object){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}").build(objectId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(object, Object.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Object.class));
    }

    Mono<Bundle> updateBundle(Long bundleId,Mono<Bundle> bundle){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/bundles/{bundle_id}").build(bundleId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(bundle, Bundle.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, Bundle.class));
    }

    Mono<AccessMethods> updateAccessMethod(Long objectId,Long accessId,Mono<AccessMethods> accessMethods){
        Mono<ClientResponse> responseMono = restTemplate
                .method(HttpMethod.PUT)
                .uri(uriBuilder -> uriBuilder.host(drsServerUri).path("/objects/{object_id}/access/{access_id}").build(objectId, accessId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(accessMethods, AccessMethods.class))
                .exchange();

        return responseMono.flatMap(response->handleResponse(response, AccessMethods.class));
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
