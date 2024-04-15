package com.project.myBlog.controller;

import com.project.myBlog.entity.Tag;
import com.project.myBlog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TagService tagService;

    @GetMapping("/")
    public String main(Model model) {
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("tagList",tagList);
        return "index";
    }
}
