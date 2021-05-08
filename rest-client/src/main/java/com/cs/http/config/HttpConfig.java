package com.cs.http.config;

/**
 * 
 * HTTP Configuration class for various environment specific variables used in
 * consuming RESTful services
 * 
 * @author Vivek Sharma
 * 
 */
public class HttpConfig {

	private int readTimeout;
	private int connectionTimeout;

	public HttpConfig() {
		super();
		this.readTimeout = 60000;
		this.connectionTimeout = 60000;
	}

	/**
	 * Read Timeout in milliseconds. Default timeout is 60 seconds.
	 * 
	 * @return
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * 
	 * Set Read Timeout in milliseconds.
	 * 
	 * @param readTimeout
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * 
	 * Connection Timeout in milliseconds. Default timeout is 60 seconds.
	 * 
	 * @return
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * 
	 * Set Connection Timeout in milliseconds.
	 * 
	 * @param connectionTimeout
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	@Override
	public String toString() {
		return "{ readTimeout=" + readTimeout + ", connectionTimeout=" + connectionTimeout + " }";
	}
}
