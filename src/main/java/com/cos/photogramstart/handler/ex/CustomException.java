package com.cos.photogramstart.handler.ex;

public class CustomException extends RuntimeException {
	
	// 객체를 구분할 때!!
	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message); // 부모클래스에 던진다 (get함수가 이미 구현되어있기때문에)
	}
}
