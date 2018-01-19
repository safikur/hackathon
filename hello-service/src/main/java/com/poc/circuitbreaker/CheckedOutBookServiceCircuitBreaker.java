package com.poc.circuitbreaker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class CheckedOutBookServiceCircuitBreaker {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${bookservice.checkedout.url}")
	private String checkedOutBooksUrl;
	
	@HystrixCommand(fallbackMethod="getDefaultCheckedOutBooks")
	public List<String> getCheckedOutBooks(){
		List<String> list = restTemplate.getForObject("http://"+checkedOutBooksUrl, List.class);
		return list;
	}
	
	public List<String> getDefaultCheckedOutBooks(){
		List<String> list = new ArrayList<>();
		list.add("No checked-out books. Plesae try sometimes later...");
		return list;
	}
}
