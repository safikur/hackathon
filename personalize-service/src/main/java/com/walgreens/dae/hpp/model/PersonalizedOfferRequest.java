package com.walgreens.dae.hpp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalizedOfferRequest {
	@JsonProperty(value="memberId", required=true)
	private String loyaltyMemberId;
	
	@JsonProperty(value="locationNbr", required=true)
	private String locationNbr;
	
	public String getLoyaltyMemberId() {
		return loyaltyMemberId;
	}
	public void setLoyaltyMemberId(String loyaltyMemberId) {
		this.loyaltyMemberId = loyaltyMemberId;
	}
	public String getLocationNbr() {
		return locationNbr;
	}
	public void setLocationNbr(String locationNbr) {
		this.locationNbr = locationNbr;
	}
}
