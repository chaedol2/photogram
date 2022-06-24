package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) X => 성능강화
	public Page<Image> 이미지스토리(int principalId, Pageable pageable){// Import주의 => org.springframework.data.domain.Pageable
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		return images;
	}
	
	@Value("${file.path}") // application.yml 에 적혀있는 file path를 찾아간다
	private String uploadForlder;
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); // uuid 99.99%겹치지않는다.
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 업로드한 파일명 가져오기
		System.out.println("이미지 파일이름 : " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadForlder+imageFileName);
		
		// 통신 I.O -> 예외가 발생할 수 있다. (예를들어 하드디스크에 파일이 없다)
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
		
//		System.out.println(imageEntity.toString());
	}
}
