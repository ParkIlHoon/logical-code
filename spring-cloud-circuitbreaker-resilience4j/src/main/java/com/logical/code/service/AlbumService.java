package com.logical.code.service;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RestTemplate restTemplate;

    @Retryable(value = CallNotPermittedException.class)
    public String getAlbumList() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        String url = "https://jsonplaceholder.typicode.com/albums";

        return circuitBreaker.run(() -> restTemplate.getForObject(url, String.class), throwable -> getDefaultAlbumList());
    }

    public String getDefaultAlbumList() {
        return "";
    }
}
