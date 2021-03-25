package com.example.demo;

//@Component
public class RestTemplateInterceptor  { //implements ClientHttpRequestInterceptor {

//    @Autowired
//    private Tracer tracer;
//
//    private static final TextMapSetter<HttpRequest> setter = (carrier, key, value) -> carrier.getHeaders().set(key, value);

   // @Override
  //  public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
//        HttpHeaders headers = httpRequest.getHeaders();
//        System.out.println("HEADERS_BEFORE" + headers);
//        String spanName = httpRequest.getMethodValue() +  " " + httpRequest.getURI().toString();
//        if (tracer == null){
//            System.out.println("tracer is null");
//        } else {
//            System.out.println("trace is not null");
//        }
//        SpanBuilder spanBuilder = tracer.spanBuilder(spanName);
//        Span currentSpan = spanBuilder.setSpanKind(SpanKind.CLIENT).startSpan();
//
//        try (Scope scope = currentSpan.makeCurrent()) {
//            //getPropagators().getTextMapPropagator().inject(Context.current(), httpRequest, setter);
//            HttpHeaders headers1 = httpRequest.getHeaders();
//            System.out.println("HEADERS_AFTER" + headers1);
//            ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
//            return response;
//        } finally {
//            currentSpan.end();
//        }

//        HttpHeaders headers = httpRequest.getHeaders();
//        System.out.println("HEADERS_BEFORE" + headers);
//
//        Span span = Span.current();
//        String spanId = span.getSpanContext().getSpanId();
//        String traceId = span.getSpanContext().getTraceId();
//        System.out.println("SPANID interceptor:" + spanId);
//        System.out.println("TRACEID interceptor:" + traceId);
//        span.getSpanContext().getTraceState().asMap().forEach((key, value) -> System.out.println("key: " + key + " value: " + value));
//        System.out.println("Span interceptor: " +  span.toString());
//
//        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
//        HttpHeaders headers1 = response.getHeaders();
//        System.out.println("HEADERS_AFTER" + headers1);
//        return response;
    //}
}
