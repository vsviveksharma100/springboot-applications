/**
 * 
 */
package com.cs.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.cs.http.client.AbstractJsonClient;

/**
 * 
 * JSON implementation of Rest Client. Testing our application we have used open
 * api https://reqres.in which provide GET, POST, PUT, DELETE api's. Read
 * Timeout is also tested with delayed response from api
 * 
 * @author Vivek Sharma
 *
 */
@Service
@ConfigurationProperties(prefix = "application.json")
public class JsonClientImplementation extends AbstractJsonClient {

	private static Logger logger = LoggerFactory.getLogger(JsonClientImplementation.class);

	private static final String GET_USER = "/api/users/2";
	private static final String POST_USER = "/api/users";
	private static final String PUT_USER = "/api/users/2";
	private static final String DELETE_USER = "/api/users/2";
	private static final String READ_TIMEOUT = "/api/users?delay=10";

	private String endpointUrl;

	public JsonClientImplementation() {
	}

	@Override
	protected void onServiceInit() {
		logger.info("======= Json Client Implementation Initialized =======");
	}

	public void invokeAllFunctions() {
		getUsers();
		postUser();
		putUser();
		deleteUser();
		readTimeout();
	}

	public String getUsers() {
		String url = getEndpointUrl() + GET_USER;
		HttpHeaders headers = createCommonHeaders();
		String response = invokeGetRequest(url, headers, String.class);
		logger.info(String.format("====== Invoked GET Response JSON : %s ======", response));
		return response;
	}

	public boolean postUser() {
		String url = getEndpointUrl() + POST_USER;
		HttpHeaders headers = createCommonHeaders();
		String payload = "{" + "    \"name\": \"morpheus\"," + "    \"job\": \"leader\"" + "}";
		String response = invokePostRequest(url, payload, headers, String.class);
		logger.info(String.format("====== Invoked POST Response JSON : %s ======", response));
		return true;
	}

	public boolean putUser() {
		String url = getEndpointUrl() + PUT_USER;
		HttpHeaders headers = createCommonHeaders();
		String payload = "{" + "    \"name\": \"morpheus\"," + "    \"job\": \"zion resident\"" + "}";
		String response = invokePutRequest(url, payload, headers, String.class);
		logger.info(String.format("====== Invoked PUT Response JSON : %s ======", response));
		return true;
	}

	public boolean deleteUser() {
		String url = getEndpointUrl() + DELETE_USER;
		HttpHeaders headers = createCommonHeaders();
		String response = invokeDeleteRequest(url, headers, String.class);
		logger.info(String.format("====== Invoked DELETE Response JSON : %s ======", response));
		return true;
	}

	public String readTimeout() {
		// read timeout is set for 2 seconds and response will returned in 10 seconds

		String url = getEndpointUrl() + READ_TIMEOUT;
		HttpHeaders headers = createCommonHeaders();
		logger.info(String.format("====== Invoked GET Response Read Timeout JSON ======"));

		String response = null;
		try {
			response = invokeGetRequest(url, headers, String.class);
		} catch (Exception e) {
			if (e instanceof ResourceAccessException)
				logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * @return
	 */
	private HttpHeaders createCommonHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

}
