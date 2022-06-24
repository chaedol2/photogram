package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // Lombok
@Entity // 디비에 테이블 생성
@Table(
		uniqueConstraints= {
				@UniqueConstraint(
						name="subscribe_uk",
						columnNames= {"fromUserId", "toUserId"}
				)	
		}
) // 외울필요는 없다 fromUserId와 toUserId 사이의 중복체크를 없애는 방법이다.
public class Subscribe {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name="fromUserId") // camel 표기법으로 바꾸기(언더바지우는방법)
	@ManyToOne // 구독하는애 N : 1 User
	private User fromUser; //구독하는애
	
	@JoinColumn(name="toUserId") // camel 표기법으로 바꾸기(언더바지우는방법)
	@ManyToOne // 구독받는애 N : 1 User
	private User toUser; //구독받는애
	
	private LocalDateTime createDate;
	
	@PrePersist //DB에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
