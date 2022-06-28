package com.cos.photogramstart.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity //해당 파일로 시큐리티를 활성화
@Configuration //LoC 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super 삭제 -> 기존 시큐리티가 가지고있는 기능이 다 비활성화됨.
		
		//csrf토큰 끄기(포스트맨으로 가입이된다.)
		http.csrf().disable();
		
		// 해당주소만 인증이 필요하고 나머지주소는 모두허용함. 로그인페이지 "/auth/signin"으로 이동 성공하면 "/'로 이동.
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "comment/**", "/api/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin") // GET요청시
			.loginProcessingUrl("/auth/signin") // POST -> 스프링 스큐리티가 로그인 프로세스 진행
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login() // form로그인도 하는데, oauth2로그인도 할꺼야!
			.userInfoEndpoint() // oauth2로그인을 하면 최종응답을 회원정보를 바로 받을 수 있다.
			.userService(oAuth2DetailsService);
	}
	
}
