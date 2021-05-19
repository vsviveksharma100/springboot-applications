/**
 * 
 */
package com.resilience.config;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

/**
 * @author Vivek Sharma
 *
 */

@Configuration
public class Resilience4jConfig {

	public static final List<Class<? extends Throwable>> EXCEPTIONS = Arrays.asList(IOException.class,TimeoutException.class, IllegalStateException.class);
	
	public Resilience4jConfig() {
	}
	
	@Bean
	public RetryRegistry retryRegistry() {
		RetryConfig config = RetryConfig.custom().maxAttempts(3)
				.waitDuration(Duration.ofMillis(3000))
				.retryExceptions(IOException.class,TimeoutException.class, IllegalStateException.class)
				.failAfterMaxAttempts(true)
				.build();
		
		return RetryRegistry.of(config);
	}

	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
				  .failureRateThreshold(50)
				  .slowCallRateThreshold(50)
				  .waitDurationInOpenState(Duration.ofMillis(1000))
				  .slowCallDurationThreshold(Duration.ofSeconds(2))
				  .permittedNumberOfCallsInHalfOpenState(3)
				  .minimumNumberOfCalls(10)
				  .slidingWindowType(SlidingWindowType.TIME_BASED)
				  .slidingWindowSize(5)
				  .build();
		return CircuitBreakerRegistry.of(circuitBreakerConfig);
		
	}
}
