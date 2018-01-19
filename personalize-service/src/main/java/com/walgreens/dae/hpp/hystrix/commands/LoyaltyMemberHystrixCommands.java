package com.walgreens.dae.hpp.hystrix.commands;

import java.util.List;
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
public class LoyaltyMemberHystrixCommands {
	private static final Logger log = LoggerFactory.getLogger(LoyaltyMemberHystrixCommands.class);
	
	@Value("${hbaseurl.sevenPartKey}")
	private String loyaltyMemberKeyHBaseUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="get7PartKeyFallBack")
	public Future<List<String>> get7PartKeysByMemberId(String memberId){
		return new AsyncResult<List<String>>(){

			@Override
			public List<String> invoke() {
				String url = "http://"+loyaltyMemberKeyHBaseUrl+"/"+memberId;
				log.info("7 PART KEY URL::"+url);
				@SuppressWarnings("unchecked")
				List<String> keys = restTemplate.getForObject(url, List.class);
				log.info("7 PART KEYS RESPONSE::"+keys);
				return keys;
			}
			
		};
	}
	
	@SuppressWarnings("unused")
	private List<String> get7PartKeyFallBack(String memberId) throws Exception{
		log.error("Failed to get 7PartKeys for member:"+memberId);
		throw new HppException("Failed to get 7PartKeys for member:"+memberId);
	}
}
