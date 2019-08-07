package com.ega.datarepositorysevice.controller;

import com.ega.datarepositorysevice.model.Error;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import java.net.URI;
import java.util.Iterator;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class HandlerUtils {
    public static final String ACCESS_METHODS_PATH_VARIABLE = "access_id";
    public static final String BUNDLE_PATH_VARIABLE = "bundle_id";
    public static final String OBJECT_PATH_VARIABLE = "object_id";

    public static Mono<Long> retrievePathVariable(ServerRequest request, String pathVariable) throws IllegalArgumentException {
        return Mono.just(request.pathVariable(pathVariable))
                .map(Long::parseLong);

    }

    public static <T> Mono<ServerResponse> returnOkResponse(Mono<T> mono, Error notFoundError) {
        Mono<ServerResponse> notFound = ServerResponse
                .status(HttpStatus.NOT_FOUND)
                .body(BodyInserters.fromObject(notFoundError));
        return returnOkResponse(mono)
                .switchIfEmpty(notFound)
                .onErrorResume(e ->
                        ServerResponse
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(BodyInserters.fromObject(notFoundError)));
    }

    public static <T> Mono<ServerResponse> returnOkResponse(Mono<T> mono) {
        return mono.flatMap(accessMethods -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(accessMethods)));
    }

    public static <T> Mono<ServerResponse> returnOkResponse(T object) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(object));
    }

    public static Mono<ServerResponse> returnOkResponse() {
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.empty());
    }

    public static Mono<ServerResponse> returnBadRequest(Throwable e) {
        String errorMessage = String.format("The request is malformed. Reason: %S", e.getMessage());
        Error badRequestError = new Error(errorMessage, HttpStatus.BAD_REQUEST);
        return ServerResponse.badRequest()
                .body(BodyInserters.fromObject(badRequestError));
    }

    public static Mono<ServerResponse> returnNotFound(Throwable e) {
        String errorMessage = String.format(" Reason: %S", e.getMessage());
        Error notFoundError = new Error(errorMessage, HttpStatus.NOT_FOUND);
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .body(BodyInserters.fromObject(notFoundError));
    }

    public static Mono<ServerResponse> returnInternalError(Throwable e) {
        String errorMessage = String.format(" Reason: %S", e.getMessage());
        Error notFoundError = new Error(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BodyInserters.fromObject(notFoundError));
    }

    public static <T> Mono<ServerResponse> returnBadRequest(Set<ConstraintViolation<T>> constraintViolations) {
        String constraintsErrorMessage = generateErrorMessageFromConstraints(constraintViolations);

        String errorMessage = String.format("The request is malformed. Reason: %S\n", constraintsErrorMessage);
        Error badRequestError = new Error(errorMessage, HttpStatus.BAD_REQUEST);
        return ServerResponse.badRequest()
                .body(BodyInserters.fromObject(badRequestError));
    }

    public static <T> Mono<ServerResponse> returnCreatedResponse(Mono<T> mono) {
        return mono.flatMap(accessMethods -> ServerResponse.created(URI.create(""))
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(accessMethods)));
    }

    public static <T> Mono<ServerResponse> returnCreatedResponse(T mono) {
        return ServerResponse.created(URI.create(""))
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(mono));
    }

    public static Mono<ServerResponse> handleError(Throwable e) {
        if (e instanceof IllegalArgumentException) {
            return returnBadRequest(e);
        } else if (e instanceof EmptyResultDataAccessException) {
            return returnNotFound(e);
        } else {
            return returnInternalError(e);
        }
    }

    public static Mono<ServerResponse> returnNotFound() {
        String errorMessage = String.format(" Reason: Path is not found");
        Error notFoundError = new Error(errorMessage, HttpStatus.NOT_FOUND);
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .body(BodyInserters.fromObject(notFoundError));
    }

    public static Mono<ServerResponse> returnEmptyBody() {
        return HandlerUtils.returnBadRequest(new IllegalArgumentException("Request body is empty"));
    }

    public static <T> String generateErrorMessageFromConstraints(Set<ConstraintViolation<T>> constraintViolations) {
        String constraintsErrorMessage = "";
        for (Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<T> constraintViolation = iterator.next();
            constraintsErrorMessage = constraintsErrorMessage
                    .concat(String.format("Error in attribute: %s, Error value: %s, Error cause: %s \n", constraintViolation.getPropertyPath(), constraintViolation.getInvalidValue(), constraintViolation.getMessage()));
        }
        return constraintsErrorMessage;
    }

}
