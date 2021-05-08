/**
 * 
 */
package com.cs.http.client;

import com.cs.http.config.DataBinding;

/**
 * 
 * Wrapper implementation for consuming JSON based RESTful service
 * 
 * @author Vivek Sharma
 *
 */
public abstract class AbstractJsonClientService extends AbstractHttpClient {

	public AbstractJsonClientService() {
	}

	@Override
	protected DataBinding getDataBinding() {
		return DataBinding.JSON;
	}

}
