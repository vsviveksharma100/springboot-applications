/**
 * 
 */
package com.resilience.service;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.bulkhead.event.BulkheadOnCallFinishedEvent;
import io.github.resilience4j.bulkhead.event.BulkheadOnCallPermittedEvent;
import io.github.resilience4j.bulkhead.event.BulkheadOnCallRejectedEvent;
import io.github.resilience4j.core.EventConsumer;
import io.github.resilience4j.decorators.Decorators;

/**
 * @author Vivek Sharma
 *
 */
@Service
public class BulkHeadServiceImpl extends AbstractService {

	@Autowired
	private ThreadPoolBulkheadRegistry bulkheadRegistry;

	private RestTemplate restTemplate;
	private static final String url = "https://reqres.in/api/users/2";
	Supplier<String> response;
	ThreadPoolBulkhead bulkhead;

	public BulkHeadServiceImpl() {
		this.restTemplate = new RestTemplate();
	}

	@PostConstruct
	private void OnInit() {
		configure();

		response = () -> restCall();
	}

	/**
	 * 
	 */
	private void configure() {
		bulkhead = bulkheadRegistry.bulkhead(getClass().getName());
		bulkhead.getEventPublisher().onCallFinished(new EventConsumer<BulkheadOnCallFinishedEvent>() {

			@Override
			public void consumeEvent(BulkheadOnCallFinishedEvent event) {
				System.out.println("============= onCallFinished =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("event.event.getBulkheadName() " + event.getBulkheadName());
				System.out.println("============= onCallFinished =============");
			}
		});

		bulkhead.getEventPublisher().onCallPermitted(new EventConsumer<BulkheadOnCallPermittedEvent>() {

			@Override
			public void consumeEvent(BulkheadOnCallPermittedEvent event) {
				System.out.println("============= onCallPermitted =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("event.event.getBulkheadName() " + event.getBulkheadName());
				System.out.println("============= onCallPermitted =============");
			}
		});

		bulkhead.getEventPublisher().onCallRejected(new EventConsumer<BulkheadOnCallRejectedEvent>() {

			@Override
			public void consumeEvent(BulkheadOnCallRejectedEvent event) {
				System.out.println("============= onCallRejected =============");
				System.out.println("event.getEventType() " + event.getEventType());
				System.out.println("event.getCreationTime() " + event.getCreationTime());
				System.out.println("event.event.getBulkheadName() " + event.getBulkheadName());
				System.out.println("============= onCallRejected =============");
			}
		});
	}

	public String businessCall() {
		try {
			return Decorators.ofSupplier(response).withThreadPoolBulkhead(bulkhead).withFallback(th -> fallback(th))
					.get().toCompletableFuture().get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

	private String restCall() {
		return restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
	}

	private String fallback(Throwable th) {
		return "Bulkhead Fallback Method Response";
	}

}
