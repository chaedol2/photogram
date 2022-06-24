package com.cos.photogramstart.handler.ex;

public class CustomApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CustomApiException(String message) {
		super(message); // 부모클래스에 던진다 (get함수가 이미 구현되어있기때문에)
	}
}

