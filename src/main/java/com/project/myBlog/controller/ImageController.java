package com.project.myBlog.controller;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.project.myBlog.entity.Image;
import com.project.myBlog.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

    public final ImageRepository imageRepository;

    @PostMapping(value="/img", produces = "application/json")
    @ResponseBody
    public JsonObject uploadImgFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("uuid") String uuid) {

        log.debug("@@@@@@@@@@@@@ file :{}",multipartFile.toString());
        log.debug("@@@@@@@@@@@@@ uuid :{}",uuid);

        JsonObject jsonObject = new JsonObject();
        String fileRoot = "";
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = uuid + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장

            Image uploadImage = Image.createImg(fileRoot,savedFileName,uuid);
            imageRepository.save(uploadImage);

            jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }
}
