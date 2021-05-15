package com.logical.code.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CustomCircuitBreakerConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {

        // Circuit Breaker 설정
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                // 실패율 threshold 퍼센트
                .failureRateThreshold(50)
                // 서킷이 OPEN 에서 HALF-OPEN 으로 변경되기 전 대기 시간
                .waitDurationInOpenState(Duration.ofSeconds(1))
                // 서킷의 상태가 CLOSED 일 때 요청의 결과를 기록하기 위한 슬라이딩 윈도의 크기
                .slidingWindowSize(2)
                .build();

        // TimeLimiter 설정
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                // Timeout 값
                .timeoutDuration(Duration.ofSeconds(4))
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }
}
