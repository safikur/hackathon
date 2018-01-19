package com.walgreens.dae.hpp.bo;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("LoyaltyMemberKeysBO")
public class LoyaltyMemberKeysBOImpl implements LoyaltyMemberKeysBO {
	private static final Logger log = LoggerFactory.getLogger(LoyaltyMemberKeysBOImpl.class);
	
	@Override
	public List<String> get7PartKeysByMemberId(String memberId) {
		log.debug("Entering get7PartKeysByMemberId()");
		
		String[] offerKeys = testData().split("\\|");
		
		log.debug("Exiting get7PartKeysByMemberId()");
		return Arrays.asList(offerKeys);
	}

	private String testData() {
		String temp = "offer1|offer2|offer3|offer4|offer5";
		return temp;
	}
}
