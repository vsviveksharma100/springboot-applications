/**
 * 
 */
package com.cs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs.model.Employee;

/**
 * @author Vivek Sharma
 *
 */
@Service
public class EmployeeService {

	private List<Employee> employees;

	public EmployeeService() {
		this.employees = new ArrayList<>();
	}

	public String add(Employee employee) {
		if (employee == null)
			throw new RuntimeException("Employee cant be null");

		employees.add(employee);

		return "Employee added sucessfully";
	}

	public List<Employee> fetchEmployees() {
		return new ArrayList<>(employees);
	}

}
