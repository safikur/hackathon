package com.walgreens.dae.hpd.defaultoffer.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/default")
public class DefaultOfferController {
	private Logger log = LoggerFactory.getLogger(DefaultOfferController.class);
	
	@Value("#{'${offers.default}'.split(',')}")
	private List<String> defaultOffers;
	
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public List<String> getDefaultOffers(String memberId, String storeNbr) {
		log.debug("Entering getDefaultOffers()");

		List<String> uniqueKeys = new ArrayList<>();
		uniqueKeys.addAll(defaultOffers);
		log.debug("Exiting getDefaultOffers()");
		return uniqueKeys;
	}
}
