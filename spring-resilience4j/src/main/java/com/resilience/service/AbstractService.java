/**
 * 
 */
package com.resilience.service;

import org.springframework.web.client.ResourceAccessException;

/**
 * @author Vivek Sharma
 *
 */
public class AbstractService {

	public AbstractService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Business Logic Generating IllegalStateException
	 * 
	 * @return
	 */
	protected String businessLogicIllegalStateExp() {
		System.out.println("Business Logic Generating IllegalStateException");
		throw new IllegalStateException("Business Logic Generating IllegalStateException");
	}

	/**
	 * 
	 * Business Logic Generating ResourceAccessException
	 * 
	 * @return
	 */
	protected String businessLogicResourceAccessExp() {
		System.out.println("Business Logic Generating ResourceAccessException");
		throw new ResourceAccessException("Business Logic Generating ResourceAccessException");
	}
}
