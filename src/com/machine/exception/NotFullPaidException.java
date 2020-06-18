package com.machine.exception;

public class NotFullPaidException extends Exception {

	private String message;
	public NotFullPaidException(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
}
