package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userId) {

		// 아래 TIP처럼 하지않는경우 findById를 통해 id값을 하나하나 찾아야한다.
//		userRepository.findById(userId);
//		imageRepository.findById(imageId);
		
		//TIP (객체를 만들 때 id 값만 담아서 insert 할 수 있다.)
		// 대신 return 시에 image 객체와 user 객체는 id값만 가지고 있는 빈객체를 리턴받는다.
		Image image = new Image();
		image.setId(imageId);
		
		//1. user정보가 필요할때
		// 만약 user정보가 필요하다면 : userRepository를 DI하고 데이터를 가져온다.
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
		});
		
		//2. user정보가 필요없을때
//		User user = new User();
//		user.setId(userId);
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		
		//1. user정보가 필요할때
		comment.setUser(userEntity);

		//2. user정보가 필요없을때
//		comment.setUser(user);
		
		
		return commentRepository.save(comment); //Comment객체로 반환된다. 리턴정보에 image객체와 user객체내부입력하지 않은 정보는 모두 null로 반환된다.
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		try {
			commentRepository.deleteById(id);			
		} catch(Exception e) {
			throw new CustomApiException(e.getMessage()); // CustomApiException은 데이터를 리턴하는 컨트롤러에사용, CustomException은 html파일을 리턴하는 컨트롤러에서 사용
		}
	}
}
