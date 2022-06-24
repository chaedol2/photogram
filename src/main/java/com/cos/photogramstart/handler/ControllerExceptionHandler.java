package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 예외처리를 다 잡아낸다.
public class ControllerExceptionHandler {
	//Validation관련
	@ExceptionHandler(CustomValidationException.class) //런타입 예외 모든상황 가로챔
	public String validationException(CustomValidationException e) {
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할 때는 Script 좋음.
		// 2. Ajax통신 - CMRespDto
		// 3. Android통신 - CMRespDto
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			//자바스크립트를 리턴
			return Script.back(e.getErrorMap().toString()); //에러 메시지 리턴			
		}
	}
	
	@ExceptionHandler(CustomException.class) //런타입 예외 모든상황 가로챔
	public String exception(CustomException e) {
			return Script.back(e.getMessage());
	}
	
	@ExceptionHandler(CustomValidationApiException.class) //런타입 예외 모든상황 가로챔
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		//데이터를 리턴
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST); //에러 메시지 리턴
	}
	
	//나머지 예외
	@ExceptionHandler(CustomApiException.class) //구독관련 예외
	public ResponseEntity<?> ApiException(CustomApiException e) {
		//데이터를 리턴
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST); //에러 메시지 리턴
	}
}
