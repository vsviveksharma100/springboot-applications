/**
 * 
 */
package com.cs.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Vivek Sharma
 *
 */

@Configuration
public class SwaggerConfig {

	/**
	 * 
	 */
	public SwaggerConfig() {
	}
	
	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("com.cs.controller"))
	      .paths(PathSelectors.regex("/.*"))
	      .build()
	      .apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Springboot-Swagger Implementation", 
	      "Springboot Swagger integration and demo", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("John Doe", "www.example.com", "myeaddress@test.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}

}
