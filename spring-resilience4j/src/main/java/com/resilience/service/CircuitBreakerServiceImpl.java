/**
 * 
 */
package com.resilience.service;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnFailureRateExceededEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnResetEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSlowCallRateExceededEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.EventConsumer;
import io.github.resilience4j.decorators.Decorators;

/**
 * @author Vivek Sharma
 *
 */
@Service
public class CircuitBreakerServiceImpl {

	@Autowired
	private CircuitBreakerRegistry circuitBreakerRegistry;

	CircuitBreaker circuitBreaker;

	@Autowired
	private ServiceA serviceA;

	private Supplier<String> validException;

	public CircuitBreakerServiceImpl() {
	}

	@PostConstruct
	private void onInit() {
		configure();
		validException = () -> serviceA.businessLogicIllegalStateExp();
	}

	/**
	 * 
	 */
	private void configure() {
		circuitBreaker = circuitBreakerRegistry.circuitBreaker(getClass().getName());

		circuitBreaker.getEventPublisher()
				.onCallNotPermitted(new EventConsumer<CircuitBreakerOnCallNotPermittedEvent>() {

					@Override
					public void consumeEvent(CircuitBreakerOnCallNotPermittedEvent event) {
						System.out.println("============= onCallNotPermitted =============");
						System.out.println("event.getEventType() " + event.getEventType());
						System.out.println("event.event.getCircuitBreakerName() " + event.getCircuitBreakerName());
						System.out.println("event.getCreationTime() " + event.getCreationTime());
						System.out.println("============= onCallNotPermitted =============");
					}
				});

		circuitBreaker.getEventPublisher().onError(new EventConsumer<CircuitBreakerOnErrorEvent>() {

			@Override
			public void consumeEvent(CircuitBreakerOnErrorEvent event) {
				System.out.println("============= OnError =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.event.getCircuitBreakerName() " + event.getCircuitBreakerName());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("event.getElapsedDuration() " + event.getElapsedDuration());
				System.out.println("event.getThrowable() " + event.getThrowable());
				System.out.println("============= OnError =============");

			}
		});

		circuitBreaker.getEventPublisher()
				.onFailureRateExceeded(new EventConsumer<CircuitBreakerOnFailureRateExceededEvent>() {

					@Override
					public void consumeEvent(CircuitBreakerOnFailureRateExceededEvent event) {
						System.out.println("============= onFailureRateExceeded =============");
						System.out.println("event.getEventType() " + event.getEventType());
						System.out.println("event.event.getCircuitBreakerName() " + event.getCircuitBreakerName());
						System.out.println("event.event.getFailureRate() " + event.getFailureRate());
						System.out.println("event.getCreationTime() " + event.getCreationTime());
						System.out.println("============= onFailureRateExceeded =============");

					}
				});

		circuitBreaker.getEventPublisher().onReset(new EventConsumer<CircuitBreakerOnResetEvent>() {

			@Override
			public void consumeEvent(CircuitBreakerOnResetEvent event) {
				System.out.println("============= onReset =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.event.getCircuitBreakerName() " + event.getCircuitBreakerName());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("============= OnError =============");

			}
		});

		circuitBreaker.getEventPublisher()
				.onSlowCallRateExceeded(new EventConsumer<CircuitBreakerOnSlowCallRateExceededEvent>() {

					@Override
					public void consumeEvent(CircuitBreakerOnSlowCallRateExceededEvent event) {
						System.out.println("============= onSlowCallRateExceeded =============");
						System.out.println("event.getEventType() " + event.getEventType());
						System.out.println("event.event.getCircuitBreakerName() " + event.getCircuitBreakerName());
						System.out.println("event.getCreationTime() " + event.getCreationTime());
						System.out.println("event.getSlowCallRate() " + event.getSlowCallRate());
						System.out.println("============= onSlowCallRateExceeded =============");

					}
				});

		circuitBreaker.getEventPublisher().onStateTransition(new EventConsumer<CircuitBreakerOnStateTransitionEvent>() {

			@Override
			public void consumeEvent(CircuitBreakerOnStateTransitionEvent event) {
				System.out.println("============= onStateTransition =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.event.getCircuitBreakerName() " + event.getCircuitBreakerName());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("event.getStateTransition() " + event.getStateTransition());
				System.out.println("============= onStateTransition =============");

			}
		});
	}

	public boolean update(boolean enable) {
		serviceA.setThrowExp(enable);
		return serviceA.isThrowExp();
	}

	public String circbuitBreakerWithValidException() {
		return Decorators.ofSupplier(validException).withCircuitBreaker(circuitBreaker).withFallback(th -> fallBack(th))
				.get();
	}

	/**
	 * @param th
	 * @return
	 */
	private String fallBack(Throwable th) {
		return "Circuit Breaker Fallback Method Response";
	}

}
