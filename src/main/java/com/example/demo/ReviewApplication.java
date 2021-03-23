package com.example.demo;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.common.InstrumentationLibraryInfo;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@SpringBootApplication
public class ReviewApplication {

	@Autowired
	private RestTemplateInterceptor restTemplateInterceptor;

	@Bean
	public RestTemplate getRestTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(Collections.singletonList(restTemplateInterceptor));
		return restTemplate;
	}

	private static final String tracerName = "reviewsSpan";

	@Bean
	public Tracer getTracer() {
		Tracer tracer = GlobalOpenTelemetry.getTracer(tracerName);
		System.out.println("The tracer is " + tracer);
		SpanProcessor logProcessor = SimpleSpanProcessor.create(new LoggingSpanExporter());
		SdkTracerProvider tracerProvider = SdkTracerProvider.builder().addSpanProcessor(logProcessor).build();
		OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).build();
		return tracer;
	}

	public static void main(String[] args) {
		SpringApplication.run(ReviewApplication.class, args);
	}

}
