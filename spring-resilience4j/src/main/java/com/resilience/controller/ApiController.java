/**
 * 
 */
package com.resilience.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resilience.service.BulkHeadServiceImpl;
import com.resilience.service.CircuitBreakerServiceImpl;
import com.resilience.service.RetryServiceImpl;

/**
 * @author Vivek Sharma
 *
 */

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private RetryServiceImpl retryService;

	@Autowired
	private CircuitBreakerServiceImpl breakerService;

	@Autowired
	private BulkHeadServiceImpl bulkHeadService;

	public ApiController() {
	}

	@GetMapping("/retry/with-valid-exception")
	public ResponseEntity<String> validException() {
		return new ResponseEntity<String>(retryService.retryWithValidException(), HttpStatus.OK);
	}

	@GetMapping("/retry/without-valid-exception")
	public ResponseEntity<String> withoutValidException() {
		return new ResponseEntity<String>(retryService.retryWithNoValidException(), HttpStatus.OK);
	}

	@GetMapping("/circuit-breaker/with-valid-exception")
	public ResponseEntity<String> cbWithoutValidException() {
		return new ResponseEntity<String>(breakerService.circbuitBreakerWithValidException(), HttpStatus.OK);
	}

	@GetMapping("/circuit-breaker/service-enable/{enable}")
	public ResponseEntity<Boolean> cbEnable(@PathVariable boolean enable) {
		return new ResponseEntity<Boolean>(breakerService.update(enable), HttpStatus.OK);
	}

	@GetMapping("/bulkhead")
	public ResponseEntity<String> bulkhead() {
		return new ResponseEntity<String>(bulkHeadService.businessCall(), HttpStatus.OK);
	}
}
