/**
 * 
 */
package com.resilience.service;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.resilience.config.Resilience4jConfig;

import io.github.resilience4j.core.EventConsumer;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.event.RetryOnErrorEvent;
import io.github.resilience4j.retry.event.RetryOnRetryEvent;
import io.vavr.control.Try;

/**
 * 
 * Retry Service implementing Resilience4j Retry. <RetryRegistry> has been
 * pre-configured with no. of retries, time interval, validation exception on
 * which retry will occur. <Retry> derived from <RetryRegistry> is configured to
 * listen on onRetry, onError events that occur and same event is used to get
 * current details like no. of retry attempts, Last Throwable, etc. <Supplier>
 * is used to generate the retry scenario. <Supplier> recover method can be used
 * to handle when all the retry events are exhausted. Here we can generate a
 * default response or throw exception back to the consumer
 * 
 * Implementation also shows how we can configure fallback method and recover
 * option in-case all the retry attempts are exhausted
 * 
 * @author Vivek Sharma
 *
 */
@Service
public class RetryService {

	@Autowired
	private RetryRegistry retryRegistry;

	private Supplier<String> retryValidException;
	private Supplier<String> retryNoValidException;
	private Retry retry;

	public RetryService() {
	}

	@PostConstruct
	private void onInit() {
		confirgureRetry();

		retryValidException = () -> businessLogicWithRetry();
		retryNoValidException = () -> businessLogicWithoutRetry();
	}

	/**
	 * Create Retry option from Retry Registry that will provide pre-configured
	 * retry values. Attach EventPublisher which provides event on onRetry when
	 * retry is attempted, onError when all retries have been exhausted, etc.
	 */
	private void confirgureRetry() {

		retry = retryRegistry.retry(getClass().getName());

		retry.getEventPublisher().onRetry(new EventConsumer<RetryOnRetryEvent>() {

			@Override
			public void consumeEvent(RetryOnRetryEvent event) {
				System.out.println("============= OnRetry =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.getNumberOfRetryAttempts() " + event.getNumberOfRetryAttempts());
				System.out.println("event.getName() " + event.getName());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("event.getLastThrowable() " + event.getLastThrowable());
				System.out.println("event.getWaitInterval() " + event.getWaitInterval());
				System.out.println("============= OnRetry =============");
			}
		});

		retry.getEventPublisher().onError(new EventConsumer<RetryOnErrorEvent>() {

			@Override
			public void consumeEvent(RetryOnErrorEvent event) {
				System.out.println("============= OnError =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.getNumberOfRetryAttempts() " + event.getNumberOfRetryAttempts());
				System.out.println("event.getName() " + event.getName());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("event.getLastThrowable() " + event.getLastThrowable());
				System.out.println("============= OnError =============");

			}
		});
	}

	/**
	 * 
	 * Retry Scenario where Exception occurs in business logic and retry is
	 * attempted due to valid exception which configured in RetryRegistry.
	 * Implementation shows use of fallback method which will called when all the
	 * retry attempts are exhausted
	 * 
	 * 
	 * @return
	 */
	public String retryWithValidException() {

		return Decorators.ofSupplier(retryValidException).withRetry(retry)
				.withFallback(Resilience4jConfig.EXCEPTIONS, th -> fallbackAfterExhaustion(th)).get();

	}

	/**
	 * Retry Scenario where Exception occurs in business logic and retry is not
	 * attempted because exception is not configured in RetryRegistry.
	 * Implementation shows use of recover method which can be used with
	 * <Try.ofSupplier>
	 * 
	 * 
	 * @return
	 */
	public String retryWithNoValidException() {
		Supplier<String> decorateSupplier = Retry.decorateSupplier(retry, retryNoValidException);

		return Try.ofSupplier(decorateSupplier).recover(throwable -> {
			System.out.println("Recover Called for Retry with no valid exception");
			System.out.println(throwable);
			throw new RuntimeException(throwable);
		}).get();

	}

	/**
	 * 
	 * Fallback method
	 * 
	 * @param throwable
	 * @return
	 */
	public String fallbackAfterExhaustion(Throwable throwable) {
		System.out.println(throwable);
		return "Fallback Method Response after all retry attempts are exhausted";
	}

	/**
	 * Business Logic Generating Exception
	 * 
	 * @return
	 */
	private String businessLogicWithRetry() {
		System.out.println("Business Logic with valid Exception. Retry will occur ......");
		throw new IllegalStateException("Calling from business logic with retry");
	}

	/**
	 * 
	 * Business Logic Generating Exception
	 * 
	 * @return
	 */
	private String businessLogicWithoutRetry() {
		System.out.println("Business Logic without valid Exception. Retry will not occur ......");
		throw new ResourceAccessException(
				"Calling from business logic with retry but not with valid exception. Retry Didnt occured");
	}
}
