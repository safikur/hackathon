package com.walgreens.dae.hpp.exception;

public class HppException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	
	public HppException() {
		super();
	}
	
	public HppException(String message) {
		this.message = message;
	}
	
	public HppException(Throwable cause) {
		super(cause);
		this.message = cause.getMessage();
	}
	
	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
