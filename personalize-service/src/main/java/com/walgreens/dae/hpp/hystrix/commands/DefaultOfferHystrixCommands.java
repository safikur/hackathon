package com.walgreens.dae.hpp.hystrix.commands;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.walgreens.dae.hpp.exception.HppException;

@Component
public class DefaultOfferHystrixCommands {
private static final Logger log = LoggerFactory.getLogger(UniqueIdHystrixCommands.class);
	
	@Value("${defaultoffer.serviceurl}")
	private String defaultOffersUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="defaultOffersFallback")
	public List<String> getDefaultOffers(){
		log.debug("Entering getDefaultOffers()");
		String url = "http://"+defaultOffersUrl;
		@SuppressWarnings("unchecked")
		List<String> defaultOffers = restTemplate.getForObject(url, List.class);
		log.debug("Exiting getDefaultOffers()");
		return defaultOffers;
	}
	
	public List<String> defaultOffersFallback() throws Exception{
		log.error("Failed to get default offers");
		throw new Exception("Exception: Failed to get default offers");
	}
}
