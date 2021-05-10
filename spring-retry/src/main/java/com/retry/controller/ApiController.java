/**
 * 
 */
package com.retry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vivek Sharma
 *
 */

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private DummyService dummyService;

	public ApiController() {
	}

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<String>(dummyService.getResponse(), HttpStatus.OK);
	}

}
