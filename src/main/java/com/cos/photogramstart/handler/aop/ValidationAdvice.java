package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component // RestController, Service 모든 것들이 Component를 상속해서 만들어져 있다.
@Aspect
public class ValidationAdvice {
	
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
//		System.out.println("web api 컨트롤러===========================================");
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) { //@valid 에 문제가 발생하면 bindingResult에 에러가 생긴다
					Map<String, String> errorMap = new HashMap<>();//에러를 담을 에러맵 생성
					
					for(FieldError error : bindingResult.getFieldErrors()) {//bindingResult에 필드에러
						errorMap.put(error.getField(), error.getDefaultMessage());// key: error.getField() value: error.getDefaultMessage() 로 담는다.
					}
					throw new CustomValidationApiException("유효성 검사 실패함", errorMap);//런타임 예외상황 강제 발동
				}
			}
		}
		// proceedingJoinPoint => 함수의 모든 곳에 접근할 수 있는 변수
		// 지정한 함수보다 apiAdvice()가 먼저 실행한다.
		
		return proceedingJoinPoint.proceed(); // 지정한 함수가 실행됨.
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
//		System.out.println("web 컨트롤러===========================================");
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) { //@valid 에 문제가 발생하면 bindingResult에 에러가 생긴다
					Map<String, String> errorMap = new HashMap<>();//에러를 담을 에러맵 생성
					
					for(FieldError error : bindingResult.getFieldErrors()) {//bindingResult에 필드에러
						errorMap.put(error.getField(), error.getDefaultMessage());// key: error.getField() value: error.getDefaultMessage() 로 담는다.
					}
					throw new CustomValidationException("유효성 검사 실패함", errorMap);//런타임 예외상황 강제 발동
				}
				
			}
		}
		
		return proceedingJoinPoint.proceed();
	}
}
