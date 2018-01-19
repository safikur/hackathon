package com.poc.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.poc.zull.filters.ErrorFilter;
import com.poc.zull.filters.PostFilter;
import com.poc.zull.filters.PreFilter;
import com.poc.zull.filters.RouteFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public PreFilter preFilter() {
	    return new PreFilter();
	}
	
	@Bean
	public PostFilter postFilter() {
	    return new PostFilter();
	}
	
	@Bean
	public ErrorFilter errorFilter() {
	    return new ErrorFilter();
	}
	
	@Bean
	public RouteFilter routeFilter() {
	    return new RouteFilter();
	}
}
