package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

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
public class Image { //N, 1
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String caption; // 예시) 오늘 나 너무 피곤해!
	private String postImageUrl; // 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 - DB에 그 저장된 경로를 insert
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId") // Foriegn Key생성
	@ManyToOne(fetch = FetchType.EAGER) // 이미지를 select하면 조인해서 User정보를 같이 들고온다.
	private User user; // 1, 1
	
	// 이미지 좋아요
	
	// 댓글
	
	private LocalDateTime createDate;
	
	@PrePersist //DB에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	//오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User부분을 출력되지 않게 함.(무한참조)
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl
//				+ ", createDate=" + createDate + "]";
//	}
}
