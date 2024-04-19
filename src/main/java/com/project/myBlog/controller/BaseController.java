//package com.project.myBlog.controller;
//
//
//import com.project.myBlog.repository.TagRepository;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//@ControllerAdvice("thymeleaf")
//public class BaseController {
//
//    private TagRepository tagRepository;
//
//
//    @ModelAttribute
//    public void handleRequest(Model model) {
//
//        model.addAttribute("tags", tagRepository.findAll());
//    }
//}
