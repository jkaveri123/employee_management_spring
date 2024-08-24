package com.jsp.Employee_management.exception;

public class PwdNotFound extends RuntimeException{
	String msg="pwd is not found";

	public PwdNotFound(String string) {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
 
}
