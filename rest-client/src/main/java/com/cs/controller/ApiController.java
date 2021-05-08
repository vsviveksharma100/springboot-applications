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
import com.cs.impl.XmlClientImplementation;

/**
 * Rest Controller for Testing JSON and XML clients
 * 
 * @author Vivek Sharma
 *
 */

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private JsonClientImplementation jsonService;

	@Autowired
	private XmlClientImplementation xmlService;

	public ApiController() {
	}

	@GetMapping("/invoke-json-apis")
	public ResponseEntity<String> json() {
		jsonService.invokeAllFunctions();
		return new ResponseEntity<String>("Hello World Json !! See application logs.", HttpStatus.OK);
	}

	@GetMapping("/invoke-xml-apis")
	public ResponseEntity<String> xml() {
		xmlService.getXmlData();
		return new ResponseEntity<String>("Hello World Xml !! See application logs.", HttpStatus.OK);
	}

}
