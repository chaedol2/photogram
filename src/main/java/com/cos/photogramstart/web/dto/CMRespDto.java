package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 모든필드의 생성자 자동생성
@NoArgsConstructor // 파라미터가 없는 생성자를 생성(final이 있는경우 초기화할수없어 에러발생 -> 강제로 @NoArgsConstructor(force=true)를 통해 생성자 만들기가능
@Data
public class CMRespDto<T> { // 여러곳에서 사용하기 때문에 제네릭으로 T를 주어 변화에 대응한다.
	private int code; //1(성공), -1(실패)
	private String message;
	private T data;
}
