package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model,  @AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto dto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());
		model.addAttribute("dto", dto);
		return "/user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 방법 1 추천
//		System.out.println("세션 정보 : " + principalDetails.getUser()); // 유저를 호출할 때 Image객체를 호출하면서 오류가발생하므로 껐다.
		
		// 방법 2 비추
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//		System.out.println("직접찾은 세션정보 : " + mPrincipalDetails.getUser());
		
		// 시큐리티 태그라이브러리로 헤더에 정의해놓아서 안해줘도 된다.
//		model.addAttribute("principal", principalDetails.getUser());
		return "/user/update";
	}
	
}
