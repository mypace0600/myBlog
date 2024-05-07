package com.project.myBlog.controller;

import com.project.myBlog.entity.Image;
import com.project.myBlog.repository.ImageRepository;
import com.project.myBlog.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    private final ResourceLoader resourceLoader;

    @PostMapping("/image")
    public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) {
        try {
            Image uploadFile = imageService.store(file);
            return ResponseEntity.ok().body("/image/" + uploadFile.getId());
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> serveFile(@PathVariable Integer id){
        try {
            Image uploadFile = imageService.load(id);
            Resource resource = resourceLoader.getResource("file:" + uploadFile.getFilePath());
            return ResponseEntity.ok().body(resource);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }
}
