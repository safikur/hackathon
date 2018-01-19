package com.walgreens.dae.hpp.bo;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("UniqueIdBO")
public class UniqueIdBOImpl implements UniqueIdBO {
	private static final Logger log = LoggerFactory.getLogger(UniqueIdBOImpl.class);

	@Override
	public String getUniqueIdBySevenPartKey(String sevenPartKey, String versionNbr) {
		log.trace("Entering getUniqueIdBySevenPartKey() : "+this.getClass().getName());
		log.trace("Exiting getUniqueIdBySevenPartKey() : "+this.getClass().getName());
		return UUID.randomUUID().toString();
	}	
}
