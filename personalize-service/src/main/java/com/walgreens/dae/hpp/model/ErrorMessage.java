package com.walgreens.dae.hpp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("errorSrc")
	private String errorSrc;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getErrorSrc() {
		return errorSrc;
	}
	public void setErrorSrc(String errorSrc) {
		this.errorSrc = errorSrc;
	}
	
}
