package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor // 모든 생성자 생성
@NoArgsConstructor // 빈생성자 생성
@Data
public class UserProfileDto {
	private boolean pageOwnerState;
	private int ImageCount; // view 페이지에서 연산을 줄이기위해 만들어가라
	private boolean subscribeState;
	private int subscribeCount;
	private User user;
}
