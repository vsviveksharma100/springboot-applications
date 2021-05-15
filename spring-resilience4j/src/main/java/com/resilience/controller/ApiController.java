/**
 * 
 */
package com.resilience.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resilience.service.RetryService;

/**
 * @author Vivek Sharma
 *
 */

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private RetryService service;

	public ApiController() {
	}

	@GetMapping("/retry/with-valid-exception")
	public ResponseEntity<String> validException() {
		return new ResponseEntity<String>(service.retryWithValidException(), HttpStatus.OK);
	}

	@GetMapping("/retry/without-valid-exception")
	public ResponseEntity<String> withoutValidException() {
		return new ResponseEntity<String>(service.retryWithNoValidException(), HttpStatus.OK);
	}

}
