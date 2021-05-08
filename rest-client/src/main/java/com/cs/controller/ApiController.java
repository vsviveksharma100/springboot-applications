/**
 * 
 */
package com.cs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.impl.JsonClientImplementation;

/**
 * @author Vivek Sharma
 *
 */

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private JsonClientImplementation jsonService;

	public ApiController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/invoke-json-apis")
	public ResponseEntity<String> hello() {
		jsonService.invokeAllFunctions();
		return new ResponseEntity<String>("Hello World!!", HttpStatus.OK);
	}

}
