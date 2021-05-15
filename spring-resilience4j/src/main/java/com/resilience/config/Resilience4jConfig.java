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

}
