package com.project.myBlog.service;

import com.project.myBlog.entity.Image;
import com.project.myBlog.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    @DisplayName("temp image file save test")
    void tempFileSave() {
        // Given
        Image temp = new Image();
        temp.setFileName("no_img");
        temp.setSaveFileName("no_img");
        temp.setContentType("image/png");
        temp.setFilePath("/Users/hyunsu/myBlog/src/main/resources/static/images/2f253085-4033-4a93-a189-84347ff86781no_img.png");

        // When
        Image savedImg = imageService.tempFileSave(); // ImageService의 tempFileSave 메서드 실행

        imageRepository.flush();

        // Then
        Image savedImage = imageRepository.findById(savedImg.getId()).orElseThrow(EntityNotFoundException::new);
        assertNotNull(savedImage); // 저장된 이미지가 null이 아닌지 확인
        assertEquals("no_img", savedImage.getFileName()); // 파일 이름이 일치하는지 확인
        assertEquals("no_img", savedImage.getSaveFileName()); // 저장된 파일 이름이 일치하는지 확인
        assertEquals("image/png", savedImage.getContentType()); // 컨텐츠 타입이 일치하는지 확인
        assertEquals("/Users/hyunsu/myBlog/src/main/resources/static/images/2f253085-4033-4a93-a189-84347ff86781no_img.png", savedImage.getFilePath()); // 파일 경로가 일치하는지 확인
    }
}