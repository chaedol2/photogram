package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException {
	
	// 객체를 구분할 때!!
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidationException(String message, Map<String, String> errorMap) {
		super(message); // 부모클래스에 던진다 (get함수가 이미 구현되어있기때문에)
		this.errorMap = errorMap;
	}
	
	public Map<String, String> getErrorMap(){
		return errorMap;
	}
}
