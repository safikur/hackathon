package com.walgreens.dae.hpp.hystrix.commands;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.walgreens.dae.hpp.exception.HppException;

@Component
public class PrintVersionHystrixCommands {
	private Logger log = LoggerFactory.getLogger(PrintVersionHystrixCommands.class);
	
	@Value("${hbaseurl.store.printversion}")
	private String printVersionHBaseUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="printVersionNbrFallback")
	public Future<String> getPrintVersionNbrAsync(final Integer storeNbr){
		return new AsyncResult<String>() {

			@Override
			public String invoke() {
				String requestUrl = "http://" + printVersionHBaseUrl;
				log.info("PrintVersion URL::"+requestUrl);
				String printVersion = restTemplate.getForObject(requestUrl, String.class);
				log.info("PrintVersion Response::"+printVersion);
				return printVersion;
			}
			
		};
	}
	
	@SuppressWarnings("unused")
	private String printVersionNbrFallback(Integer storeNbr) throws Exception{
		log.debug("Entering printVersionNbrFallback()");
		log.error("Failed to get store print version number for store:"+storeNbr);
		log.debug("Exiting printVersionNbrFallback()");
		throw new HppException("Failed to get store print version number for store:"+storeNbr);
	}
}
