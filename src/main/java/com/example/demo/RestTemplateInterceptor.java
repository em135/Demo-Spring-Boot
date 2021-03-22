package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        System.out.println("HEADERS_BEFORE" + headers);
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        HttpHeaders headers1 = response.getHeaders();
        System.out.println("HEADERS_AFTER" + headers1);
        return response;
    }
}
