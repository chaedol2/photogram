package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 롬복어노테이션 : final 필드를 DI를 모두만들어준다.
@Controller // 1. IoC등록 2. 파일을 리턴하는 컨트롤러
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	private final AuthService authService; // 전역변수에 final이 걸려있다면 생성자 주입 혹은 객체생성시 초기화를 무조건해주어야한다.
	
	//의존성 주입
	//방법 1 어노테이션 주입 -> @Service어노테이션이 있는것을 알아서 찾는다.
//	@Autowired
//	private AuthService authService;
	
	//방법2 생성자주입 -> @Service어노테이션이 있는것을 알아서 찾는다.
//	private AuthService authService;
//	public AuthController(AuthService authService) {
//		this.authService=authService;
//	}
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	// 회원가입버튼 -> /auth/signup -> /auth/signin
	// 회원가입버튼 아무발생안함 403에러 -> CSRF 토큰이 있기때문에 안된다.
	// 이유 : 첫 로그인화면에서 회원가입버튼을 누를때 이미 CSRF 토큰을 Security가 회원가입 Form의 Input 태그에 CSRF="KFC" 속성을 심고 화면을 준다.
	//         하지만 시큐리티가 주었던 KFC속성과 화면을 이동할때 다른 토큰일경우 에러가발생. (CSRF토큰을 사용하는 이유 : 정상적인접근을통해 진행하는지 구분하기 위해사용)
	// 해결방법 : 시큐리티컨피그에서 csrf를 꺼준다. 
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { //key = value형식 (x-www-form-urlencoded)
		//전처리
		if(bindingResult.hasErrors()) { //@valid 에 문제가 발생하면 bindingResult에 에러가 생긴다
			Map<String, String> errorMap = new HashMap<>();//에러를 담을 에러맵 생성
			
			for(FieldError error : bindingResult.getFieldErrors()) {//bindingResult에 필드에러
				errorMap.put(error.getField(), error.getDefaultMessage());// key: error.getField() value: error.getDefaultMessage() 로 담는다.
				System.out.println("==================================");
				System.out.println(error.getDefaultMessage());
				System.out.println("==================================");
			}
			throw new CustomValidationException("유효성 검사 실패함", errorMap);//런타임 예외상황 강제 발동
		}else {
			log.info(signupDto.toString());
			//User <- SignupDto
			User user = signupDto.toEntity();
			log.info(user.toString());
			User userEntity = authService.회원가입(user);
			System.out.println(userEntity);
			return "auth/signin";			
		}
		
	}
}
