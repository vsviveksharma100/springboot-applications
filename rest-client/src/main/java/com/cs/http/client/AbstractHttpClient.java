/**
 * 
 */
package com.cs.http.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.cs.http.config.DataBinding;
import com.cs.http.config.HttpConfig;

/**
 * 
 * Abstract and Generic Implementation of Http Client for consuming Restful
 * services.
 * 
 * @author Vivek Sharma
 *
 */
public abstract class AbstractHttpClient {

	private static Logger logger = LoggerFactory.getLogger(AbstractHttpClient.class);

	private HttpConfig config;
	private DataBinding dataBinding;
	private RestConsumer httpClient;

	public AbstractHttpClient() {
	}

	protected void onInit() {
		configure();

		// Callback Method for implementing class
		onServiceInit();
	}

	private void configure() {
		configurRestTemplate();
		configureRetry();
	}

	/**
	 * Create and Configure Rest Template with custom or default values
	 */
	private void configurRestTemplate() {
		RestTemplate restTemplate;
		if (config == null) {
			logger.info("No HTTP Configuration found. Default values will be used to configure RestTemplate.");
			restTemplate = getRestTemplate(new HttpConfig());
		} else {
			restTemplate = getRestTemplate(config);
		}

		this.httpClient = new RestConsumer(restTemplate);

	}

	/**
	 * 
	 */
	private void configureRetry() {

	}

	/**
	 * 
	 * Create RestTemplate with Http Configuration
	 * 
	 * @param httpConfig
	 * @return
	 */
	protected RestTemplate getRestTemplate(HttpConfig httpConfig) {

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectionRequestTimeout(httpConfig.getConnectionTimeout());
		factory.setReadTimeout(httpConfig.getReadTimeout());

		return new RestTemplate(factory);
	}

	/**
	 * 
	 * Invoke Http GET request
	 * 
	 * @param <V>
	 * @param url
	 * @param headers
	 * @param responseClass
	 * @return
	 */
	protected <V> V invokeGetRequest(String url, HttpHeaders headers, Class<V> responseClass) {
		HttpEntity<String> httpEntity = httpClient.getHttpEntity(headers);
		return invokeRequest(url, httpEntity, HttpMethod.GET, responseClass);
	}

	/**
	 * Invoke Http POST request
	 * 
	 * @param <V>
	 * @param <T>
	 * @param url
	 * @param payload
	 * @param headers
	 * @param responseClass
	 * @return
	 */
	protected <V, T> V invokePostRequest(String url, T payload, HttpHeaders headers, Class<V> responseClass) {
		HttpEntity<T> httpEntity = httpClient.getHttpEntity(headers, payload);
		return invokeRequest(url, httpEntity, HttpMethod.POST, responseClass);
	}

	/**
	 * 
	 * Invoke Http PUT request
	 * 
	 * @param <V>
	 * @param <T>
	 * @param url
	 * @param payload
	 * @param headers
	 * @param responseClass
	 * @return
	 */
	protected <V, T> V invokePutRequest(String url, T payload, HttpHeaders headers, Class<V> responseClass) {
		HttpEntity<T> httpEntity = httpClient.getHttpEntity(headers, payload);
		return invokeRequest(url, httpEntity, HttpMethod.PUT, responseClass);
	}

	/**
	 * 
	 * Invoke Rest Consumer Client
	 * 
	 * @param <V>
	 * @param url
	 * @param httpEntity
	 * @param httpMethod
	 * @param responseClass
	 * @return
	 */
	private <V> V invokeRequest(String url, HttpEntity<?> httpEntity, HttpMethod httpMethod, Class<V> responseClass) {
		return httpClient.invokeHttpRequest(url, httpEntity, httpMethod, responseClass);
	}

	/**
	 * Callback method for implementing class for configuration after initialization
	 */
	protected abstract void onServiceInit();

	/**
	 * DataBinding of the implementing class
	 * 
	 * @return DataBinding
	 */
	protected abstract DataBinding getDataBinding();
}
