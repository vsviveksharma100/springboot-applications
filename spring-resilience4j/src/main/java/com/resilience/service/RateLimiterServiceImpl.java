/**
 * 
 */
package com.resilience.service;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.core.EventConsumer;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.event.RateLimiterOnFailureEvent;
import io.github.resilience4j.ratelimiter.event.RateLimiterOnSuccessEvent;

/**
 * @author Vivek Sharma
 *
 */

@Service
public class RateLimiterServiceImpl {

	@Autowired
	private RateLimiterRegistry rateLimiterRegistry;

	RateLimiter rateLimiter;

	Supplier<String> response;

	public RateLimiterServiceImpl() {
	}

	@PostConstruct
	private void onInit() {
		configure();
		response = () -> businessCall();

	}

	/**
	 * 
	 */
	private void configure() {
		rateLimiter = rateLimiterRegistry.rateLimiter(getClass().getName());

		rateLimiter.getEventPublisher().onFailure(new EventConsumer<RateLimiterOnFailureEvent>() {

			@Override
			public void consumeEvent(RateLimiterOnFailureEvent event) {
				System.out.println("============= onFailure =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.getRateLimiterName() " + event.getRateLimiterName());
				System.out.println("event.getNumberOfPermits() " + event.getNumberOfPermits());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("============= onFailure =============");
			}
		});

		rateLimiter.getEventPublisher().onSuccess(new EventConsumer<RateLimiterOnSuccessEvent>() {

			@Override
			public void consumeEvent(RateLimiterOnSuccessEvent event) {
				System.out.println("============= onSuccess =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.getRateLimiterName() " + event.getRateLimiterName());
				System.out.println("event.getNumberOfPermits() " + event.getNumberOfPermits());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("============= onSuccess =============");
			}
		});
	}

	public String getResponse() {
		return Decorators.ofSupplier(response).withRateLimiter(rateLimiter).withFallback(th -> fallback(th)).get();
	}

	private String fallback(Throwable th) {
		return "Rate Limiter Failure Response";
	}

	private String businessCall() {
		return "Rate Limiter Successful Response";
	}

}
