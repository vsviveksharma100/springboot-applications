/**
 * 
 */
package com.cs;

/**
 * @author Vivek Sharma
 *
 */
public class RestClientException extends RuntimeException {

	private static final long serialVersionUID = 6165591872239416125L;

	/**
	 * 
	 */
	public RestClientException() {
	}

	/**
	 * @param arg0
	 */
	public RestClientException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public RestClientException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RestClientException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public RestClientException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
