package com.poc.circuitbreaker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class AvailableBookServiceCircuitBreaker {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${bookservice.available.url}")
	private String availableBooksUrl;
	
	@HystrixCommand(fallbackMethod="getDefaultBooks")
	public List<String> getAvailableBooks(){
		List<String> list = restTemplate.getForObject("http://"+availableBooksUrl, List.class);
		return list;
	}
	
	public List<String> getDefaultBooks(){
		List<String> list = new ArrayList<>();
		list.add("No available books now. Plesae try sometimes later..");
		return list;
	}
	
}
