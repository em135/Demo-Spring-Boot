package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@SpringBootApplication
public class ReviewApplication {

//	@Autowired
//	private RestTemplateInterceptor restTemplateInterceptor;

	@Bean
	public RestTemplate getRestTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		//restTemplate.setInterceptors(Collections.singletonList(new RestTemplateInterceptor()));
		return restTemplate;
	}

	private static final String tracerName = "reviewsSpan";

//	@Bean
//	public Tracer getTracer() {
//		Tracer tracer = GlobalOpenTelemetry.getTracer(tracerName);
//		System.out.println("The tracer is " + tracer);
//		SpanProcessor logProcessor = SimpleSpanProcessor.create(new LoggingSpanExporter());
//		SdkTracerProvider tracerProvider = SdkTracerProvider.builder().addSpanProcessor(logProcessor).build();
//		OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).build();
//		return tracer;
//	}

	public static void main(String[] args) {
		SpringApplication.run(ReviewApplication.class, args);
	}

}
