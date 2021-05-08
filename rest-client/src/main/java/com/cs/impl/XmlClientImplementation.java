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

import com.cs.http.client.AbstractXmlClientSerice;

/**
 * 
 * XML implementation of Rest Client. Testing our application we have used open
 * api http://api.nbp.pl which provide GET api with xml content type.
 * 
 * @author Vivek Sharma
 *
 */
@Service
@ConfigurationProperties(prefix = "application.xml")
public class XmlClientImplementation extends AbstractXmlClientSerice {

	private static Logger logger = LoggerFactory.getLogger(XmlClientImplementation.class);

	private String endpointUrl;

	public XmlClientImplementation() {
	}

	@Override
	protected void onServiceInit() {
		logger.info("===== Xml Client Initialized =====");
	}

	public String getXmlData() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
		String response = invokeGetRequest(getEndpointUrl(), headers, String.class);
		logger.info(String.format("====== Invoked GET XML : %s ======", response));
		return response;
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

}
