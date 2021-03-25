package com.example.demo;

import io.opentelemetry.api.trace.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RestController
public class ReviewController {

    @Autowired
    private RestTemplate restTemplate;

    private final static Boolean ratings_enabled = Boolean.valueOf(System.getenv("ENABLE_RATINGS"));
    private final static String star_color = System.getenv("STAR_COLOR") == null ? "black" : System.getenv("STAR_COLOR");
    private final static String services_domain = System.getenv("SERVICES_DOMAIN") == null ? "" : ("." + System.getenv("SERVICES_DOMAIN"));
    private final static String ratings_hostname = System.getenv("RATINGS_HOSTNAME") == null ? "ratings" : System.getenv("RATINGS_HOSTNAME");
    private final static String ratings_service = "http://" + ratings_hostname + services_domain + ":9080/ratings";


    @RequestMapping("/")
    public String home() {
        return "Hello Docker OpenTelemetry World";
    }

    private String getJsonResponse (String productId, int starsReviewer1, int starsReviewer2) {
        String result = "{";
        result += "\"id\": \"" + productId + "\",";
        result += "\"reviews\": [";

        // reviewer 1:
        result += "{";
        result += "  \"reviewer\": \"Reviewer1\",";
        result += "  \"text\": \"An extremely entertaining play by Shakespeare. The slapstick humour is refreshing!\"";
        if (ratings_enabled) {
            if (starsReviewer1 != -1) {
                result += ", \"rating\": {\"stars\": " + starsReviewer1 + ", \"color\": \"" + star_color + "\"}";
            }
            else {
                result += ", \"rating\": {\"error\": \"Ratings service is currently unavailable\"}";
            }
        }
        result += "},";

        // reviewer 2:
        result += "{";
        result += "  \"reviewer\": \"Reviewer2\",";
        result += "  \"text\": \"Absolutely fun and entertaining. The play lacks thematic depth when compared to other plays by Shakespeare.\"";
        if (ratings_enabled) {
            if (starsReviewer2 != -1) {
                result += ", \"rating\": {\"stars\": " + starsReviewer2 + ", \"color\": \"" + star_color + "\"}";
            }
            else {
                result += ", \"rating\": {\"error\": \"Ratings service is currently unavailable\"}";
            }
        }
        result += "}";

        result += "]";
        result += "}";

        return result;
    }

    private RatingList getRatings(String productId) {
        int timeout = star_color.equals("black") ? 10000 : 2500;
//        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(timeout);
//        ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(timeout);

//        String temp = "http://localhost:9080/ratings/";
        ResponseEntity<RatingList> r = restTemplate.getForEntity(ratings_service + "/" + productId, RatingList.class);
//        ResponseEntity<RatingList> r = restTemplate.getForEntity(temp + productId, RatingList.class);

        int statusCode = r.getStatusCodeValue();
        if (statusCode == HttpStatus.OK.value()) {
            if (r.hasBody()){
                return r.getBody();
            }
        }

        System.out.println("Error: unable to contact " + ratings_service + " got status of " + statusCode);
        return null;
    }

    @GetMapping("/health")
    public String health() {
        return "{\"status\": \"Reviews is healthy\"}";
    }

    @GetMapping("/reviews/{productId}")
    public String bookReviewsById(@PathVariable("productId") int productId, @RequestHeader Map<String, String> headers) {
        System.out.println("PRINTING HEADERS");
        headers.forEach((key, value) -> {
            System.out.println("key:" + key + " and value:" + value);
        });

        int starsReviewer1 = -1;
        int starsReviewer2 = -1;
        Span span = Span.current();

        String spanId = span.getSpanContext().getSpanId();
        String traceId = span.getSpanContext().getTraceId();
        System.out.println("SPANID controller:" + spanId);
        System.out.println("TRACEID controller:" + traceId);
        span.getSpanContext().getTraceState().asMap().forEach((key, value) -> System.out.println("key: " + key + " value: " + value));
        System.out.println("Span controller: " +  span.toString());

        if (ratings_enabled) { //ratings_enabled
            RatingList ratingsResponse = getRatings(String.valueOf(productId));
            System.out.println("THERATINGS:" + ratingsResponse);
            if (ratingsResponse != null) {
                Rating ratings = ratingsResponse.getRatings();
                if (ratings != null) {
                    starsReviewer1 = (int) ratings.getReviewer1();
                    starsReviewer2 = (int) ratings.getReviewer2();
                }
            }
        }
        return getJsonResponse(Integer.toString(productId), starsReviewer1, starsReviewer2);
    }



}
