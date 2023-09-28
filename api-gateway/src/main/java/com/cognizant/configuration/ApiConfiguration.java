package com.cognizant.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@CrossOrigin
class ApiConfiguration {
	@Autowired
	private GatewayFilter tokenValidationFilter;
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("authorization-service",ps->ps.path("/authorization/**")
						.uri("http://localhost:8080"))

				.route("loan-service",ps->ps.path("/loan/**")
						.filters(f->f.filter(tokenValidationFilter).removeRequestHeader("Cookies"))
						.uri("http://localhost:8081"))

				.route("customer-service",ps->ps.path("/customer/**")
						.filters(f->f.filter(tokenValidationFilter).removeRequestHeader("Cookies"))
						.uri("http://localhost:8082"))

				.build();
				
		}
	
		
		
	}