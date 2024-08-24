package com.jsp.Employee_management.exception;

import java.sql.SQLIntegrityConstraintViolationException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jsp.Employee_management.util.ResponseStructure;


@RestControllerAdvice
public class EmployeeException {

	public ResponseEntity<ResponseStructure<String>> userNotFound(
			IdNotFound e) {

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStateCode(HttpStatus.NOT_FOUND.value());
		rs.setData(e.getMsg());
		rs.setMessage("id not found .pls try again with new data");
		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.NOT_FOUND);

	}
	public ResponseEntity<ResponseStructure<String>> userNotFound2(
			EmailNotFound e) {

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStateCode(HttpStatus.NOT_FOUND.value());
		rs.setData(e.getMsg());
		rs.setMessage("email not found .pls try again with new data");
		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.NOT_FOUND);

	}
	public ResponseEntity<ResponseStructure<String>> userNotFound3(
			PwdNotFound e) {

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStateCode(HttpStatus.NOT_FOUND.value());
		rs.setData(e.getMsg());
		rs.setMessage("pwd not found .pls try again with new data");
		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ResponseStructure<String>> SQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException e) {

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStateCode(HttpStatus.BAD_REQUEST.value());
		rs.setData(e.getMessage());
		rs.setMessage("you cant perform this operation");
		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.BAD_REQUEST);

	}
	
	

}
