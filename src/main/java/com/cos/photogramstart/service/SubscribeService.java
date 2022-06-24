package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; // 모든 Repository는 EntityManager를 구현해서 만들어져 있는 구현체이다.
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){
		
		//쿼리 준비
		StringBuffer sb = new StringBuffer(); // 버퍼로 쿼리를 넣을 때 주의할점 -> 끝에 한칸을 꼭 띄워준다!!
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id),1,0) subscribeState, ");
		sb.append("if((?=u.id),1,0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?"); // 주의할점 -> 세미콜론 첨부하면 안된다!! 마지막은 안띄워도된다!!
		
		// 2번줄 물음표 principalId
		// 3번줄 물음표 principalId
		// 마지막 물음표 pageUserId
		
		// 쿼리 완성 java persistence query로 import
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1,  principalId)
				.setParameter(2,  principalId)
				.setParameter(3,  pageUserId);
		
		//쿼리 실행 (qlrm 라이브러리 필요 => Dto에 DB결과를 매핑하기 위해서)
		// qlrm은 스프링부트에서 기본적으로제공해주는 라이브러리가 아니다. => 데이터베이스에서 result된 결과를 자바클래스에 매핑해주는 라이브러리
		JpaResultMapper result = new JpaResultMapper();
		//한건을 리턴받을시 => result.uniqueResult(query, null)
		//여러건 리턴받을시
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		
		return subscribeDtos;
	}
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);			
		} catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		//삭제는 실패시에도 오류가 나지 않는다.
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
