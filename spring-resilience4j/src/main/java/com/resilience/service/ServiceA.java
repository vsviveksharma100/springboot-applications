/**
 * 
 */
package com.resilience.service;

import org.springframework.stereotype.Service;

/**
 * @author Vivek Sharma
 *
 */

@Service
public class ServiceA extends AbstractService {

	private boolean throwExp;

	public ServiceA() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String businessLogicIllegalStateExp() {
		if (!isThrowExp())
			return "Successful Service Call";

		return super.businessLogicIllegalStateExp();
	}

	public boolean isThrowExp() {
		return throwExp;
	}

	public void setThrowExp(boolean throwExp) {
		this.throwExp = throwExp;
	}

}
