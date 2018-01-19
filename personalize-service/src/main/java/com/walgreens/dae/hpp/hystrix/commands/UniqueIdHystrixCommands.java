package com.walgreens.dae.hpp.hystrix.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.walgreens.dae.hpp.exception.HppException;

@Component
public class UniqueIdHystrixCommands {
	
	private static final Logger log = LoggerFactory.getLogger(UniqueIdHystrixCommands.class);
	
	@Value("${hbaseurl.uniqueid}")
	private String uniqueIdHBaseUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="uniqueIdBySevenPartKeyFallback")
	public String getUniqueIdBySevenPartKey(String sevenPartKey, String versionNbr){
		
		StringBuilder url = new StringBuilder("http://").append(uniqueIdHBaseUrl)
				.append("/").append(sevenPartKey).append("/")
				.append("/").append(sevenPartKey);
		log.info("Unique ID URL::"+url.toString());
		String uniqueId = restTemplate.getForObject(url.toString(), String.class);
		log.info("Unique ID Response::"+uniqueId);
		return uniqueId;
				
	}
	
	@SuppressWarnings("unused")
	private String uniqueIdBySevenPartKeyFallback(final String sevenPartKey, final String versionNbr) throws Exception{
		log.error("Failed to get unique Id for 7PartKey:"+sevenPartKey+", versionNbr:"+versionNbr);
		throw new HppException("Failed to get unique Id for 7PartKey:"+sevenPartKey+", versionNbr:"+versionNbr);
	}
} 
