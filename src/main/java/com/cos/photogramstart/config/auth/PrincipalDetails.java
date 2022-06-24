package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	//권한 : 한개가 아닐 수 있음. (3개 이상의 권한이 있을 수 있다.), 우리는 하나만 할것이다. 그래서 Collection 함수를 리턴받는다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> { return user.getRole();}); //람다식으로 변형
		return collector;
	}

// 위 함수와 동일한 함수
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Collection<GrantedAuthority> collector = new ArrayList<>();
//		collector.add(new GrantedAuthority() {	
//			@Override
//			public String getAuthority() {
//				return user.getRole();
//			}
//		}); //자바 원본형
//		return collector;
//	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	// 현업에서는 개인정보보호법에 따라 처리해야하는 메소드들이다. 연습에서는 모두 true로 로그인 처리하였다.
	@Override
	public boolean isAccountNonExpired() { // 너 계정이 만료 되지않았니?
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 너 계정이 잠기지 않았니?
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 너 자격 증명이 만료되지 않았니?
		return true;
	}

	@Override
	public boolean isEnabled() { // 너 사용가능하니?
		return true;
	}
	
}
