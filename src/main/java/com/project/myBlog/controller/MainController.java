package com.project.myBlog.controller;

import com.project.myBlog.repository.TagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final TagRepository tagRepository;

    public MainController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("tags", tagRepository.findAll());

        return "index";
    }
    @GetMapping("/lop")
    public String post() {

        return "index";
    }


}
