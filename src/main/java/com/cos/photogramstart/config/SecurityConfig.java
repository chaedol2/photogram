package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity //해당 파일로 시큐리티를 활성화
@Configuration //LoC 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
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
			.defaultSuccessUrl("/");
	}
	
}
