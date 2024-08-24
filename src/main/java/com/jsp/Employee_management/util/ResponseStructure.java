package com.jsp.Employee_management.util;

import java.time.LocalDateTime;



import lombok.Data;

@Data
public class ResponseStructure<T> {
	private int stateCode;
	private  String message;
	private T data;
	private LocalDateTime time =LocalDateTime.now();
}