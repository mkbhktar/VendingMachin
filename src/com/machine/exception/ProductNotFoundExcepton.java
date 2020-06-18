package com.machine.exception;

public class ProductNotFoundExcepton extends Exception {
	
	private String message;
	
	
	public ProductNotFoundExcepton(String msg){
		super(msg);
	}
	
	public void getExceptionMsg(){
		System.out.println(this.message);
	}

}
