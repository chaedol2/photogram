package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
//	@Modifying // 이건 사용할 수 없다. 이유 : @Modifying 어노테이션을 사용하여 커스텀쿼리를 작성할 때 리턴값이 int만 할수 있기 때문에 (현재 Comment객체를 리턴하므로 에러남)
//	@Query(value = "INSERT INTO comment(content, imageId, userId, createDate) VALUES(:content, :imageId, :userId, now())", nativeQuery=true)
//	Comment mSave(String content, int imageId, int userId);
}
