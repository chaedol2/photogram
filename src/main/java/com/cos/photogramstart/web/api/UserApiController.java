package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;
	private final SubscribeService subscribeService;
	
	@GetMapping("/api/user/{pageUserId}/subscribe") // 페이지 주인이 구독하고있는 모든 정보가져오기
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보리스트 불러오기 성공", subscribeDto), HttpStatus.OK);
	}
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, // 위치가 중요하다. 꼭 @Valid가 적혀있는 파라미터 다음에 적는다.
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) { //@valid 에 문제가 발생하면 bindingResult에 에러가 생긴다
			Map<String, String> errorMap = new HashMap<>();//에러를 담을 에러맵 생성
			
			for(FieldError error : bindingResult.getFieldErrors()) {//bindingResult에 필드에러
				errorMap.put(error.getField(), error.getDefaultMessage());// key: error.getField() value: error.getDefaultMessage() 로 담는다.
//				System.out.println("==================================");
//				System.out.println(error.getDefaultMessage());
//				System.out.println("==================================");
			}
			throw new CustomValidationApiException("유효성 검사 실패함", errorMap);//런타임 예외상황 강제 발동
		}else {
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity); //세션정보 리로드해서 바꾸기
			return new CMRespDto<>(1, "회원수정완료", userEntity); // 응답시에 userEntity의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답한다.
		}
	}
	
}
