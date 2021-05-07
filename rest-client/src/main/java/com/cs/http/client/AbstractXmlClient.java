/**
 * 
 */
package com.cs.http.client;

import com.cs.http.config.DataBinding;

/**
 * Wrapper implementation for consuming XML based RESTful service
 * 
 * @author Vivek Sharma
 *
 */
public abstract class AbstractXmlClient extends AbstractHttpClient {

	public AbstractXmlClient() {
	}

	@Override
	protected void onServiceInit() {

	}

	@Override
	protected DataBinding getDataBinding() {
		return DataBinding.XML;
	}

}
