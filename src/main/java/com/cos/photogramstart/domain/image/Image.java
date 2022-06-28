package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
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
	@JsonIgnoreProperties({"image"}) // Likeservice.java에서 Image객체를 불러올 때 Likes를 불러오는데 Likes안에있는 image를 무한참조방지
	@OneToMany(mappedBy = "image") // 하나의 이미지에는 여러개의 좋아요가 가능하므로 @OneToMany(LAZY로딩)이고, 나는 연관관계의 주인이 아닙니다 FK만들지마세요 (mappedBy="image")이다.
	private List<Likes> likes;
	
	// 댓글
	@OrderBy("id DESC") // id로 내림차순 뽑기
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image") // mappedBy="image"에서 image는 foriegnkey 자바 변수를 적어준다.
	private List<Comment> comments;
	
	private LocalDateTime createDate;
	
	@Transient // import javax.., DB에 칼럼이 만들어지지 않는다.
	private boolean likeState;
	
	@Transient // DB에 칼럼이 만들어지지 않는다.
	private int likeCount;
	
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
