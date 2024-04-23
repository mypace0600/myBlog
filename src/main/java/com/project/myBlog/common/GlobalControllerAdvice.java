package com.project.myBlog.common;

import com.project.myBlog.entity.Tag;
import com.project.myBlog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final TagService tagService;

    @ModelAttribute("tagList")
    public List<Tag> tagList(){
        return tagService.findAll();
    }
}
