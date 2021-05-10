/**
 * 
 */
package com.retry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Vivek Sharma
 *
 */

@Service
public class DummyService {

	@Autowired
	private RetryTemplate retryTemplate;

	public DummyService() {
	}

	public String getResponse() {

		String response = "Hello....";
		try {
			response = retryTemplate.execute(new RetryCallback<String, RuntimeException>() {

				@Override
				public String doWithRetry(RetryContext context) throws RuntimeException {
					System.out.println("calling business logic.... " + context.getRetryCount());
					return businessLogic();
				}
			}, new RecoveryCallback<String>() {

				@Override
				public String recover(RetryContext context) throws Exception {
					int retryCount = context.getRetryCount();
					System.out.println("Retried " + retryCount + " times");
					return "Failed to execute business logic. Returning response from recover .....";
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;

	}

	private String businessLogic() {
		throw new RuntimeException("Failed to execute Business Logic....");
	}

}
