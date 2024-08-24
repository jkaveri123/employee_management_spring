package com.jsp.Employee_management.exception;

public class IdNotFound  extends RuntimeException{
	String msg="id not found";

	public String getMsg() {
		return msg;
	}

	

	public IdNotFound(String msg) {
		super();
		this.msg = msg;
	}



	public IdNotFound() {
		super();
	}

	
}
