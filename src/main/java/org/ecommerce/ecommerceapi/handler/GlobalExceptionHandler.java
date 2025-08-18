package org.ecommerce.ecommerceapi.handler;

import org.ecommerce.ecommerceapi.exceptions.*;
import org.ecommerce.ecommerceapi.infra.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handle(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of(
                        "erro", ex.getReason(),
                        "status", ex.getStatusCode().value()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "bad Request");

        Map<String, String> fields = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fields.put(err.getField(), err.getDefaultMessage()));
        body.put("fields", fields);

        return ResponseEntity.badRequest().body(body);
    }

    // 500 – fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleAnyException(Exception e) {
        RestErrorMessage treatResonse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(treatResonse);
    }

    // 409 - conflito de dominio
    @ExceptionHandler(ClientConflictException.class)
    public ResponseEntity<RestErrorMessage> clientConflictException(ClientConflictException exception){
        RestErrorMessage treatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(treatResponse);
    }

    // 404 - recurso nao encontrado
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<RestErrorMessage> clientNotFound(ClientNotFoundException exClient){
        RestErrorMessage treatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exClient.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(treatResponse);
    }

    // 403 - autenticado, porem sem permisao (ROLE)
    @ExceptionHandler(ClientAccessDeniedException.class)
    public ResponseEntity<RestErrorMessage> clientAcessDeniedException(ClientAccessDeniedException exception){
        RestErrorMessage treatResponse = new RestErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(treatResponse);

    }

    // 401 - nao autenticado
    @ExceptionHandler(ClientUnauthorizedException.class)
    public ResponseEntity<RestErrorMessage> clientUnauthorizedException(ClientUnauthorizedException exception){
        RestErrorMessage treatResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(treatResponse);

    }

    // 400 - bad request
    @ExceptionHandler(ClientBadRequestException.class)
    public ResponseEntity<RestErrorMessage> clientNotFound(ClientBadRequestException exClient){
        RestErrorMessage treatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exClient.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(treatResponse);
    }






}
