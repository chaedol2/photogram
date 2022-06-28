package com.cos.photogramstart.domain.likes;

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

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // Lombok
@Entity // 디비에 테이블 생성
@Table(// 중복 제거, 유니크 제약조건 => 어떤 유저1명이 1번 이미지를 좋아요 했다면 또 1번 이미지를 1번유저가 좋아요 2번 이상 할 수 없다.
		uniqueConstraints= {
				@UniqueConstraint(
						name="likes_uk",
						columnNames= {"imageId", "userId"}
				)	
		}
)
public class Likes {//마리아디비에 Like는 이미 정해진 변수라서 Likes로 하였다. N
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	// ImageService에서 Images를 불러오고 image에서 like를 불러올 때  무한참조됨
	@JoinColumn(name="imageId")
	@ManyToOne // ManyToOne은 기본 EAGER 전략이고,  OneToMany는 LAZY전략이다.
	private Image image; // 1
	
	
	@JsonIgnoreProperties({"images"}) // 무한참조 방지
	@JoinColumn(name="userId")
	@ManyToOne
	private User user; // 1
	
	private LocalDateTime createDate;
	
	// native쿼리를 쓸 경우 아래함수는 의미가 없다.
	@PrePersist //DB에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
			
			
}
