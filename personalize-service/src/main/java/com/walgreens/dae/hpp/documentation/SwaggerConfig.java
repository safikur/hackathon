//(c) Walgreen Co.  All rights reserved
package com.walgreens.dae.hpp.documentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * Swagger Configuration
 * 
 * @author Safikur Khan
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${apidocs.services.group}")
	private String groupName;

	@Value("${apidocs.services.title}")
	private String title;

	@Value("${apidocs.services.description}")
	private String description;

	@Value("${apidocs.services.version}")
	private String version;

	@Value("${apidocs.services.termsOfServiceUrl}")
	private String termsOfServiceUrl;

	@Value("${apidocs.services.contact}")
	private String contact;

	@Value("${apidocs.services.license}")
	private String license;

	@Value("${apidocs.services.licenseUrl}")
	private String licenseUrl;

	@Value("${apidocs.services.enable}")
	private boolean isDocumentationEnabled;

	/**
	 * @return Docket object
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.walgreens.dae.hpp.controller"))
				.paths(Predicates.not(PathSelectors.regex("/error")))
				.build()
				.apiInfo(apiInfo())
				.enable(isDocumentationEnabled)
				.groupName(groupName);
		
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.termsOfServiceUrl(termsOfServiceUrl)
				.license(license)
				.licenseUrl(licenseUrl)
				.version(version)
				.build();
	}
}