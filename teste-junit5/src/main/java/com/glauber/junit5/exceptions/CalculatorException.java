package com.glauber.junit5.exceptions;

public class CalculatorException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public CalculatorException() {
		super();
	}

	public CalculatorException(String message) {
		super(message);
	}
	
}