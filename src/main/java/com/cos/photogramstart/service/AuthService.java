package com.cos.photogramstart.service;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //1. IoC 트랜잭션 관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional // 트랜잭션 관리르 해준다. Write(Insert, Update, Delete)
	public User 회원가입(User user) {
		// 회원가입 진행
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);//암호화가 된 password
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");//기본적인 역할부여 //관리자는 ROLE_ADMIN으로 줄거다.
		User userEntity = userRepository.save(user);
		return userEntity;
	}
}
