package com.project.myBlog.service;

import com.project.myBlog.entity.Image;
import com.project.myBlog.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${imgUploadPath}")
    String inProject;

    public Image store(MultipartFile file) throws Exception {
        try{
            if(file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }

            String saveFileName = fileSave(inProject, file);
            Image saveImage = new Image();
            saveImage.setFileName(file.getOriginalFilename());
            saveImage.setSaveFileName(saveFileName);
            saveImage.setContentType(file.getContentType());
            saveImage.setSize(file.getResource().contentLength());
            saveImage.setFilePath(inProject.replace(File.separatorChar, '/') +'/' + saveFileName);
            imageRepository.save(saveImage);
            return saveImage;
        } catch(IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public String fileSave(String rootLocation, MultipartFile file) throws IOException {
        File uploadDir = new File(rootLocation);

        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid.toString() + file.getOriginalFilename();
        log.debug("@@@@@@@@@@@ saveFileName :{}",saveFileName);
        File saveFile = new File(rootLocation,saveFileName);
        FileCopyUtils.copy(file.getBytes(),saveFile);

        return saveFileName;
    }

    public Image load(Integer fileId) {
        return imageRepository.findById(fileId).get();
    }

}
