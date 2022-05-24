package com.claudionogueira.news.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket newsProjectAPI() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.claudionogueira.news")).paths(regex("/api.*")).build()
				.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		return new ApiInfo("News Project API REST", "API REST for news with authors and categories", "1.0",
				"Terms of Service",
				new Contact("Claudio Vinicius Nogueira", "https://portfolio-claudionogueira.netlify.app/",
						"cllaudionoggueira@gmail.com"),
				"Apache License Version 2.0", "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>());
	}
}
