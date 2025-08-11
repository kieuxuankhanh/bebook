package com.example.demo.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder<T> {
    private HttpStatus status;
    private T body;
    private String error;
    private String message;
    private HttpHeaders headers;

    public static <T> ResponseBuilder<T> create() {
        return new ResponseBuilder<>();
    }

    public ResponseBuilder<T> status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseBuilder<T> body(T body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder<T> error(String error) {
        this.error = error;
        return this;
    }

    public ResponseBuilder<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResponseBuilder<T> headers(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public ResponseBuilder<T> contentType(MediaType contentType) {
        if(headers == null){
            headers = new HttpHeaders();
        }
        headers.setContentType(contentType != null ? contentType : MediaType.APPLICATION_JSON);
        return this;
    }

    public ResponseEntity<Object> build() {
        if(status == null){
            status = HttpStatus.OK;
        }

        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(headers != null ? headers : new HttpHeaders(), status);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("timestamp", ZonedDateTime.now().toString());
        if(body != null){
            response.put("body", body);
        }
        if(error != null){
            response.put("error", error);
        }
        if(message != null){
            response.put("message", message);
        }

        HttpHeaders responseHeaders = headers != null ? headers : new HttpHeaders();
        if(responseHeaders.getContentType() != null){
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        return new ResponseEntity<>(response, responseHeaders, status);
    }
}
