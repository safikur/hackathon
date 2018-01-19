package com.walgreens.dae.hpp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalizedOfferResponse {
	@JsonProperty(value="uniqueKeys")
	private List<String> uniqueKeys;
	
	@JsonProperty(value="errorMsg")
	private ErrorMessage error;

	public List<String> getUniqueKeys() {
		return uniqueKeys;
	}
	public void setUniqueKeys(List<String> uniqueKeys) {
		this.uniqueKeys = uniqueKeys;
	}
	public ErrorMessage getError() {
		return error;
	}
	public void setError(ErrorMessage error) {
		this.error = error;
	}
}
