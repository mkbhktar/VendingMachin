package com.machine.exception;

public class ItemNotSelectedException extends Exception {

	private String message;
	public ItemNotSelectedException(String msg){
		super(msg);
		this.message = msg;
	}
	
	public void getExceptionMessage(){
		System.out.println(this.message);
	}
	
}
