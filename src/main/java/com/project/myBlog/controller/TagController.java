package com.project.myBlog.controller;

import com.project.myBlog.service.PostService;
import com.project.myBlog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final PostService postService;

}
