package com.rahil.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {
    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name="sample-api", fallbackMethod = "hardcodedResponse")
//    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    //10s => 10000 calls to the api
//    @RateLimiter(name = "default")
    @Bulkhead(name = "sample-api")
    public String sampleApi(){
        logger.info("Sample api call recieved");
//        return new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
//                String.class).getBody();

        return "sample-api";
    }

    public String hardcodedResponse(Exception ex){
        return "Fallback - response";
    }
}
