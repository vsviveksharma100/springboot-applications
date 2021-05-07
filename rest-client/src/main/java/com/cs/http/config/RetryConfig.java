/**
 * 
 */
package com.cs.http.config;

/**
 * 
 * Configuration class for defining environment specific Retry Properties
 * 
 * @author Vivek Sharma
 *
 */
public class RetryConfig {

	private boolean enabled;
	private int interval;
	private int attempts;

	public RetryConfig() {
		super();
		this.enabled = false;
		this.interval = 5000;
		this.attempts = 3;
	}

	/**
	 * Returns true if enabled and false if not. Default false.
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 
	 * Set retry option
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Retry time interval between consecutive call in milliseconds. Default 5
	 * seconds.
	 * 
	 * @return
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * Retry time interval between consecutive call in milliseconds.
	 * 
	 * @param interval
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * Total number of attempts. Default 3
	 * 
	 * @return
	 */
	public int getAttempts() {
		return attempts;
	}

	/**
	 * 
	 * Total number of attempts.
	 * 
	 * @param attempts
	 */
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

}
