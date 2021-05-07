/**
 * 
 */
package com.cs.http.client;

import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * RESTful implementation to invoke Http request with required methods. Generic
 * Implementation to handle all kind of request and content
 * 
 * @author Vivek Sharma
 *
 */
public class RestConsumer {

	private RestTemplate restTemplate;

	public RestConsumer() {
	}

	public RestConsumer(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * 
	 * Invoke Http Request
	 * 
	 * @param <V>
	 * @param url
	 * @param httpEntity
	 * @param httpMethod
	 * @param responseClass
	 * @return
	 */
	protected <V> V invokeHttpRequest(String url, HttpEntity<?> httpEntity, HttpMethod httpMethod,
			Class<V> responseClass) {

		validate(url, responseClass);
		return invoke(url, httpEntity, httpMethod, responseClass);
	}

	/**
	 * Validate request URL and Response Class
	 * 
	 * @param url
	 * @param responseClass
	 * @return
	 */
	private boolean validate(String url, Class<?> responseClass) {
		if (Strings.isBlank(url))
			throw new RestClientException("Url must not be empty.");

		else if (Objects.isNull(responseClass))
			throw new RestClientException("Response class must not be null.");

		return true;
	}

	/**
	 * 
	 * Execute Http Request and return response
	 * 
	 * @param <V>
	 * @param url
	 * @param httpEntity
	 * @param httpMethod
	 * @param responseClass
	 * @return
	 */
	protected <V> V invoke(String url, HttpEntity<?> httpEntity, HttpMethod httpMethod, Class<V> responseClass) {

		ResponseEntity<V> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, responseClass);
		if (responseEntity == null)
			return null;

		return responseEntity.getBody();
	}

	/**
	 * @return the restTemplate
	 */
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	/**
	 * @param restTemplate
	 */
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * 
	 * Create HttpEntity with HttpHeaders
	 * 
	 * @param <T>
	 * @param headers
	 * @return
	 */
	public <T> HttpEntity<T> getHttpEntity(HttpHeaders headers) {
		return new HttpEntity<T>(headers);
	}

	/**
	 * Create HttpEntity with HttpHeaders and Payload
	 * 
	 * 
	 * @param <T>
	 * @param headers
	 * @param payload
	 * @return
	 */
	public <T> HttpEntity<T> getHttpEntity(HttpHeaders headers, T payload) {
		if (payload == null)
			return getHttpEntity(headers);

		return new HttpEntity<T>(payload, headers);
	}
}
