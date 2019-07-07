package com.ega.datarepositorysevice.controller;

import com.ega.datarepositorysevice.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class HandlerUtils {
    public static final String ACCESS_METHODS_PATH_VARIABLE = "access_id";
    public static final String BUNDLE_PATH_VARIABLE = "bundle_id";
    public static final String OBJECT_PATH_VARIABLE = "object_id";

    public static Long retrievePathVariable(ServerRequest request, String pathVariable) throws IllegalArgumentException {
        String id = request.pathVariable(pathVariable);
        return Long.parseLong(id);
    }

    public static <T> Mono<ServerResponse> returnOkResponse(Mono<T> mono, Error notFoundError) {
        Mono<ServerResponse> notFound = ServerResponse
                .status(HttpStatus.NOT_FOUND)
                .body(BodyInserters.fromObject(notFoundError));

        return mono.flatMap(accessMethods -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(accessMethods)))
                .switchIfEmpty(notFound)
                .onErrorResume(e ->
                        ServerResponse
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(BodyInserters.fromObject(notFoundError)));
    }

    public static Mono<ServerResponse> returnBadRequest(IllegalArgumentException e) {
        String errorMessage = String.format("The request is malformed. Reason: %S", e.getMessage());
        Error badRequestError = new Error(errorMessage, HttpStatus.BAD_REQUEST);
        return ServerResponse.badRequest()
                .body(BodyInserters.fromObject(badRequestError));
    }


}
