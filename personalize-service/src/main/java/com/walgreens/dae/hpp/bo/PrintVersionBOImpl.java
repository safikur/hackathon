package com.walgreens.dae.hpp.bo;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walgreens.dae.hpp.hystrix.commands.PrintVersionHystrixCommands;

@Component("PrintVersionBO")
public class PrintVersionBOImpl implements PrintVersionBO {
	private static final Logger log = LoggerFactory.getLogger(PrintVersionBOImpl.class);
	
	@Autowired
	private PrintVersionHystrixCommands circuitBreaker;
	
	@Override
	public String getPrintVersionNbr(Integer locaionNbr) throws Exception{
		log.debug("Entering getPrintVersionNbr()");
		
		Future<String> printVersion = circuitBreaker.getPrintVersionNbrAsync(locaionNbr);
		
		log.debug("Exiting getPrintVersionNbr()");
		return printVersion.get();
	}

}
