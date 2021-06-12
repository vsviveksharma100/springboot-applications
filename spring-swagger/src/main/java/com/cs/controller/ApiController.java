/**
 * 
 */
package com.cs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.model.Employee;
import com.cs.service.EmployeeService;

/**
 * @author Vivek Sharma
 *
 */
@RestController
@RequestMapping("/api/employees")
public class ApiController implements ApiInfo {

	@Autowired
	private EmployeeService employeeService;

	public ApiController() {
	}

	@GetMapping
	public ResponseEntity<List<Employee>> fetchEmployees() {
		return new ResponseEntity<>(employeeService.fetchEmployees(), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.add(employee), HttpStatus.OK);
	}

}
