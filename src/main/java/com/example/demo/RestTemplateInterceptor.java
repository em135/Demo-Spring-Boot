package com.example.demo;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static io.opentelemetry.api.GlobalOpenTelemetry.getPropagators;

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private Tracer tracer;

    private static final TextMapSetter<HttpRequest> setter = (carrier, key, value) -> carrier.getHeaders().set(key, value);

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();

        System.out.println("HEADERS_BEFORE" + headers);
        String spanName = httpRequest.getMethodValue() +  " " + httpRequest.getURI().toString();
        if (tracer == null){
            System.out.println("tracer is null");
        } else {
            System.out.println("trace is not null");
        }
        SpanBuilder spanBuilder = tracer.spanBuilder(spanName);
        Span currentSpan = spanBuilder.setSpanKind(SpanKind.CLIENT).startSpan();

        try (Scope scope = currentSpan.makeCurrent()) {
            getPropagators().getTextMapPropagator().inject(Context.current(), httpRequest, setter);
            HttpHeaders headers1 = httpRequest.getHeaders();
            System.out.println("HEADERS_AFTER" + headers1);
            ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
            return response;
        } finally {
            currentSpan.end();
        }

//        HttpHeaders headers = httpRequest.getHeaders();
//        System.out.println("HEADERS_BEFORE" + headers);
//        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
//        HttpHeaders headers1 = response.getHeaders();
//        System.out.println("HEADERS_AFTER" + headers1);
//        return response;
    }
}
