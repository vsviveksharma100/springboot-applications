/**
 * 
 */
package com.cs.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cs.model.Employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Vivek Sharma
 *
 */
@Api(value = "Employee Service")
public interface ApiInfo {

	@ApiOperation(value = "View all employees")
	public ResponseEntity<List<Employee>> fetchEmployees();

	@ApiOperation(value = "Add new employee")
	public ResponseEntity<String> addEmployee(Employee employee);
}
